package com.example.myapplication.navigation.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.navigation.Login.LoginActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        ib_setting_back.setOnClickListener {
            finish()
        }

        tv_logout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e("logout", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i("logout", "로그아웃 성공. SDK에서 토큰 삭제됨")
                    val intent = Intent(App.instance, LoginActivity::class.java )
                    startActivity(intent)
                }
            }
        }

        tv_exit.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("exit", "연결 끊기 실패", error)
                }
                else {
                    Log.i("exit", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                    val intent = Intent(App.instance, LoginActivity::class.java )
                    startActivity(intent)
                }
            }
        }


    }
}