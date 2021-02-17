package com.example.myapplication

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import org.json.JSONArray

class SharedPreferenceUtil(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("searchHistory", MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun get(): String? = sharedPreferences.getString("searchHistory", "null")
    fun set(searchList: ArrayList<String>) {
        editor.putString("searchHistory", JSONArray(searchList).toString())
            .apply()
    }
}