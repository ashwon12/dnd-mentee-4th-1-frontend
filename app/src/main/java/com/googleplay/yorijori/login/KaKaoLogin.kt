package com.googleplay.yorijori.login

import android.content.Context
import android.content.Intent
import android.util.Log
import com.googleplay.yorijori.App
import com.googleplay.yorijori.MainActivity
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.data.repository.Repository
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class KaKaoLogin(context: Context) {

    private var userToken = ""
    private var userEmail = ""
    private var userName = ""
    private var userImage = ""

    private var userInfo = RecipeDTO.RequestPostLogin("", "")

    private val repository = Repository()

    private var context: Context = context

    fun getToken() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("loginWithKakaoTalk", "카카오 로그인 실패", error)
            } else if (token != null) {
                Log.i("kakao", "카카오 로그인 성공 ${token.accessToken}")

                // 로그인
                userToken = token.accessToken
                App.sharedPrefs.saveToken(userToken)
                updateKaKaoLoginUi()
            }
        }


        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d("setKakaoLoginBtn()", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                Log.d("setKakaoLoginBtn()", "${tokenInfo.id}" + "${tokenInfo.expiresIn} 초")
            }
        }

    }

    private fun updateKaKaoLoginUi() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "updateKaKaoLoginUi : 사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    "kakao", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.profileImageUrl}" +
                            "\n이메일: ${user.kakaoAccount?.email.toString()}"
                )
                /*
                  회원번호: 1642841812
                  닉네임: 함도영
                  프로필사진: https://k.kakaocdn.net/dn/dIf8Px/btq17a7W4Vj/FJiZYfnB1U6TRveYK6jQU1/img_640x640.jpg
                  이메일: hamdoyoung@naver.com
                */

                //userEmail = user.id.toString() + "@kakao" // userEmail : 1642841812@kakao <- 왜 쓰는지 이해안감
                //userEmail = "test@kakao"
                userName = user.kakaoAccount?.profile?.nickname.toString()
                userImage = user.kakaoAccount?.profile?.profileImageUrl.toString()
                userEmail = user.kakaoAccount?.email.toString()

                App.sharedPrefs.saveInfo(userEmail, userImage)
                postKakaoLoginInfo()
            }
        }
    }

    private fun postKakaoLoginInfo() {
        if (userToken != null && userEmail != null) {
            userInfo.email = App.sharedPrefs.getEmail()
            App.sharedPrefs.saveFlag("1")
            Log.d("userInfo email", userInfo.email.toString())
            repository.postLoginInfo(
                userToken,
                userInfo,
                success = {
                    it.run {
                        val data = it.data
                        // App.sharedPrefs.saveKakaoId(data.toString())
                        App.sharedPrefs.saveToken(data.toString()) // 왜 또 Token을 Save 해주는거야...
                        Log.d("data", it.data.toString())

                        val intent = Intent(App.instance, MainActivity::class.java)
                        intent.putExtra("join", 0)
                        context.startActivity(intent)
                    }
                }, fail = {
                    Log.e("postKakaoLoginInfo() 실패", "${it}")
                })
            Log.d("get token", App.sharedPrefs.getToken().toString())
            Log.d("kakao data1", App.sharedPrefs.getEmail().toString())
            Log.d("kakao data2", App.sharedPrefs.getKakaoId().toString())

            if (App.sharedPrefs.getKakaoId() == null || App.sharedPrefs.getToken() == "") { //신규
                val intent = Intent(App.instance, MainActivity::class.java)
                intent.putExtra("join", 1)
                App.sharedPrefs.saveFlag("1")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Log.d("LoginActivity", "postKakaoLoginInfo")
            } else { // 기존
                val intent = Intent(App.instance, MainActivity::class.java)
                intent.putExtra("join", 0)
                App.sharedPrefs.saveFlag("1")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Log.d("Id data", App.sharedPrefs.getKakaoId().toString())
                Log.d("카카오로그인 성공 !!", "카카오 로그인 성공!")
            }
        }
    }
}