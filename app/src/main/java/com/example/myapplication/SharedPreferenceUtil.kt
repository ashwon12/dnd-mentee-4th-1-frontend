package com.example.myapplication

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) {
    private val prefsFilenanme = "prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilenanme, MODE_PRIVATE)

    var searchHistory: String?
        get() = prefs.getString("searchHistory", "")
        set(value) = prefs.edit().putString("searchHistory", value).apply()
}