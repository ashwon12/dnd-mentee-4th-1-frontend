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
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login_main.*


class LoginActivity : BaseActivity(R.layout.activity_login_main) {
    private val RC_SIGN_IN = 0
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private var userToken = ""
    private var userEmail = ""
    private var userName = ""
    private var userId = ""
    private var userImage = ""

    private val repository = Repository()

    private var userInfo = RecipeDTO.RequestPostLogin("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setKakaoLoginBtn()
        setGoogleLoginBtn()
    }

    private fun setKakaoLoginBtn() {
        iv_kakao_login.setOnClickListener {
            val kakaoLogin = KaKaoLogin(this)
            kakaoLogin.getToken()
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