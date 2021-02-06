package com.example.myapplication.data.datasource.local

import com.example.myapplication.App
import org.json.JSONArray
import org.json.JSONException




class LocalDataSourceImpl : LocalDataSource {
    override fun saveSearch(recipe: String) {
        val searchList = getSavedSearchList()
        searchList.add(recipe)

        App.sharedPrefs.set(searchList)
    }

    override fun getSavedSearchList(): ArrayList<String> {
        val json = App.sharedPrefs.get()
        val searchedList = ArrayList<String>()

        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    searchedList.add(a.optString(i).toString())
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return searchedList
    }
}