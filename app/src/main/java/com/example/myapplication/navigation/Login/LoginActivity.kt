package com.example.myapplication.navigation.Login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.App
import com.example.myapplication.MainActivity
import com.example.myapplication.R
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


class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 0
    lateinit var mGoogleSignInClient : GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_main)

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestProfile()
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
            }
            else if (token != null) {
                Log.i("kakao", "로그인 성공 ${token.accessToken}")
                var intent = Intent(this, MainActivity::class.java)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }

        if(LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
            LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if(error != null) {
                Log.d("tag", "토큰 정보 보기 실패", error)
            }  else if(tokenInfo != null) {
                Log.d("tag", "${tokenInfo.id}" + "${tokenInfo.expiresIn} 초" )
            }
        }

        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                Log.d("google", "google logout")
            }
    }

    private fun googleLoginFunction() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("kakao", "연결 끊기 실패", error)
            }
            else {
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

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        } catch (e: ApiException) {
            // 구글 로그인 실패
            Log.w("google", "signInResult:failed code=" + e.statusCode)
        }
    }
}