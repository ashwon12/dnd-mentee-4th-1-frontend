package com.googleplay.yorijori.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.googleplay.yorijori.App
import com.googleplay.yorijori.MainActivity
import com.googleplay.yorijori.R
import com.googleplay.yorijori.base.BaseActivity
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.data.repository.Repository
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login_main.*


class LoginActivity : BaseActivity(R.layout.activity_login_main) {
    private val RC_SIGN_IN = 0
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val repository = Repository()
    private var userToken = ""
    private var userEmail = ""
    private var userName = ""
    private var userId = ""
    private var userImage = ""
    private var userInfo = RecipeDTO.RequestPostLogin("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setKakaoLoginBtn()
        setGoogleLoginBtn()
    }

    private fun setKakaoLoginBtn() {
        iv_kakao_login.setOnClickListener {

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("kakao", "카카오 로그인 실패", error)
                } else if (token != null) {
                    Log.i("kakao", "카카오 로그인 성공 ${token.accessToken}")

                    // 로그인
                    userToken = token.accessToken
                    App.sharedPrefs.saveToken(userToken)
                    updateKaKaoLoginUi()
                }
            }


            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Log.d("setKakaoLoginBtn()", "토큰 정보 보기 실패", error)
                } else if (tokenInfo != null) {
                    Log.d("setKakaoLoginBtn()", "${tokenInfo.id}" + "${tokenInfo.expiresIn} 초")
                }
            }

            mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this) {
                    Log.d("google", "google logout")
                }
        }
    }

    private fun updateKaKaoLoginUi() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "사용자 정보 요청 실패", error)
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
                Log.d("userEmail", App.sharedPrefs.getEmail().toString())
                postKakaoLoginInfo()
            }
        }
    }

    private fun setGoogleLoginBtn() {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestProfile()
                .requestIdToken(getString(R.string.client_id))
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        iv_google_login.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("kakao", "연결 끊기 실패", error)
                } else {
                    Log.i("kakao", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // 구글 로그인 성공
            Log.w("google", "signInResult:success code=" + account)
            updateGoogleLoginUi()

        } catch (e: ApiException) {
            // 구글 로그인 실패
            Log.w("google", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateGoogleLoginUi() {
        val acct = GoogleSignIn.getLastSignedInAccount(App.instance)

        if (acct != null) {
            val personName = acct?.displayName
            val personGivenName = acct?.givenName
            val personFamilyName = acct?.familyName
            val personEmail = acct?.email
            val personId = acct?.id
            val personPhoto: Uri? = acct?.photoUrl
            val personToken = acct.idToken
            val personExpire = acct?.isExpired

            userToken = acct.idToken.toString()
            Log.d("userToken google", personToken.toString() + "<<<")


            val split = personEmail!!.split("@")
            // userEmail = split[0] + "@google"
            userEmail = personEmail.toString()
            // userEmail = "123456@gmail.com"

            Log.d("userEmail", userEmail)
            userInfo.email = userEmail
            App.sharedPrefs.saveToken(userToken)
            App.sharedPrefs.saveInfo(userEmail!!, personPhoto.toString())
            postGoogleLoginInfo()
        } else {
            Log.d("google", "access fail")
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
                        startActivity(intent)
                    }
                }, fail = {
                    Log.e("postKakaoLoginInfo() 실패", "${it}")
                })
            Log.d("get token", App.sharedPrefs.getToken().toString())
            Log.d("kakao data1", App.sharedPrefs.getEmail().toString())
            Log.d("kakao data2", App.sharedPrefs.getKakaoId().toString())

            if (App.sharedPrefs.getKakaoId() == null || App.sharedPrefs.getToken() == "") {
                val intent = Intent(App.instance, MainActivity::class.java)
                intent.putExtra("join", 1)
                App.sharedPrefs.saveFlag("1")
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                Log.d("LoginActivity", "postKakaoLoginInfo")
            } else {
                //카카오 로그인 성공
                val intent = Intent(App.instance, MainActivity::class.java)
                intent.putExtra("join", 0)
                App.sharedPrefs.saveFlag("1")
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                Log.d("Id data", App.sharedPrefs.getKakaoId().toString())
                Log.d("카카오로그인 성공 !!", "카카오 로그인 성공!")
            }
        }
    }

    private fun postGoogleLoginInfo() {
        if (App.sharedPrefs.getToken() != null && userEmail != null) {
            App.sharedPrefs.saveFlag("2")
            userInfo.email = App.sharedPrefs.getEmail()
            userToken = App.sharedPrefs.getToken()!!
            repository.postLoginInfo(userToken, userInfo,
                success = {
                    it.run {
                        val data = it.data
                        // App.sharedPrefs.saveGoogleId(data.toString())
                        App.sharedPrefs.saveToken(data.toString())
                        Log.d("shared_id", App.sharedPrefs.getGoogleId().toString())
                    }
                }, fail = {
                    App.sharedPrefs.saveGoogleId(null)
                })


            Log.d("google data1", App.sharedPrefs.getEmail().toString())
            Log.d("google data2", App.sharedPrefs.getGoogleId().toString())

            if (App.sharedPrefs.getGoogleId() == null || App.sharedPrefs.getToken() == "") {
                val intent = Intent(App.instance, MainActivity::class.java)
                intent.putExtra("join", 1)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                App.sharedPrefs.saveFlag("2")
                startActivity(intent)
                finish()
                Log.e("LoginActivity", "postGoogleLoginInfo")
            } else {
                val intent = Intent(App.instance, MainActivity::class.java)
                intent.putExtra("join", 0)
                App.sharedPrefs.saveFlag("2")
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                Log.d("fail shared id ", App.sharedPrefs.getGoogleId().toString())
                Log.d("구글 성공 !!", "구글 성공!")
            }
        }
    }

}