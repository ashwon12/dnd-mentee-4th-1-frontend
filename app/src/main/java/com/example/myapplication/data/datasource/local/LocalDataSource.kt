package com.example.myapplication.data.datasource.local

interface LocalDataSource {
    fun saveSearch(recipe : String)

    fun getSavedSearchList() : List<String>
}