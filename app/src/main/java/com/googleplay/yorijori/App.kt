package com.googleplay.yorijori

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

/**
 * instance를 반환하는 class
 * Context가 필요할 때 사용하면 됩니다!!
 * App.intance
 */

class App: Application() {

    companion object {
        lateinit var instance : App
            private set
        lateinit var sharedPrefs: SharedPreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        sharedPrefs = SharedPreferenceUtil(applicationContext)

        KakaoSdk.init(instance, "7c0ae88d49666eca35738b64bba94021")
    }
}