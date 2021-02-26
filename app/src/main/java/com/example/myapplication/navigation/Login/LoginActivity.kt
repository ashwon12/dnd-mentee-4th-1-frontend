package com.example.myapplication.navigation.Login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.SharedPreferenceUtil
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login_main.*
import kotlinx.android.synthetic.main.fragment_mypage.*


class LoginActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_login_main)

        setStatusBarTransparent()

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestProfile()
                .requestIdToken(getString(R.string.client_id))
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        iv_kakao_login.setOnClickListener {
            kakaoLoginFunction()
        }

        iv_google_login.setOnClickListener {
            googleLoginFunction()
        }
    }

    private fun kakaoLoginFunction() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao", "로그인 실패", error)
            } else if (token != null) {
                Log.i("kakao", "로그인 성공 ${token.accessToken}")

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
                Log.d("tag", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                Log.d("tag", "${tokenInfo.id}" + "${tokenInfo.expiresIn} 초")
            }
        }

        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                Log.d("google", "google logout")
            }
    }

    private fun updateKaKaoLoginUi() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "사용자 정보 요청 실패", error)
            } else if (user != null) {
//                Log.i(
//                    "kakao", "사용자 정보 요청 성공" +
//                            "\n회원번호: ${user.id}" +
//                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
//                            "\n프로필사진: ${user.kakaoAccount?.profile?.profileImageUrl}" +
//                            "\n이메일동의: ${user.kakaoAccount?.isEmailVerified}" +
//                            "\n이메일: ${user.kakaoAccount?.email.toString()}"
//                )

                userEmail = user.id.toString() + "@kakao"
                // userEmail = "test11@kakao"
                userName = user.kakaoAccount?.profile?.nickname.toString()
                userImage = user.kakaoAccount?.profile?.profileImageUrl.toString()

                App.sharedPrefs.saveInfo(userEmail, userImage)
                Log.d("userEmail", App.sharedPrefs.getEmail().toString())
                postKakaoLoginInfo()
            }
        }
    }

    private fun googleLoginFunction() {
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
            repository.postLoginInfo(userToken, userInfo,
                success = {
                    it.run {
                        val data = it.data
                        // App.sharedPrefs.saveKakaoId(data.toString())
                        App.sharedPrefs.saveToken(data.toString())
                        Log.d("data", it.data.toString())
                        val intent = Intent(App.instance, MainActivity::class.java)
                        intent.putExtra("join",0)
                        startActivity(intent)
                    }
                }, fail = {
                    // App.sharedPrefs.saveKakaoId(null)
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
                Log.d("login fail!!!!", "fail")
            } else {
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
                Log.d("login fail!!!!", "fail")
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

    fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}