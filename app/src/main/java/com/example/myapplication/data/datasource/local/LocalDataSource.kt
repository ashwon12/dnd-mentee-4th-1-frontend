package com.example.myapplication.data.datasource.local

import com.example.myapplication.App
import org.json.JSONArray
import org.json.JSONException


class LocalDataSource {
    fun saveSearchWord(recipe: String) {
        val searchWordList = getSavedSearchWordList()
        if (searchWordList.contains(recipe)) { // 중복 검색어 저장하지 않기
            searchWordList.remove(recipe)// 예전 기록 지우기
        }
        searchWordList.add(recipe)

        App.sharedPrefs.set(searchWordList)
    }

    fun getSavedSearchWordList(): ArrayList<String> {
        val wordListSharedPrefs = App.sharedPrefs.get()
        val searchedList = ArrayList<String>()

        if (wordListSharedPrefs != null) {
            try {
                val jsonArray = JSONArray(wordListSharedPrefs)
                for (i in 0 until jsonArray.length()) {
                    searchedList.add(jsonArray.optString(i).toString())
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return searchedList
    }
}