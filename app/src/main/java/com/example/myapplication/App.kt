package com.example.myapplication

import android.app.Application

/**
 * instance를 반환하는 class
 * Context가 필요할 때 사용하면 됩니다!!
 * App.intance
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPrefs = SharedPreferenceUtil(applicationContext)
    }

    companion object {
        lateinit var instance: App
            private set
        lateinit var sharedPrefs: SharedPreferenceUtil
    }
}