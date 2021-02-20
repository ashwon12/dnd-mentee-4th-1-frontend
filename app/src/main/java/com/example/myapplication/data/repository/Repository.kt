package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.local.LocalDataSource
import com.example.myapplication.data.datasource.remote.RemoteDataSource
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class Repository {

    private val localDataSourceImpl = LocalDataSource()
    private val remoteMovieDataSourceImpl = RemoteDataSource()

    fun getRandomRecipes(
        success: (RecipeDTO.tempRandomRecipes) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getRandomRecipes(
            success,
            fail
        )
    }

    fun saveSearch(recipe: String) {
        localDataSourceImpl.saveSearchWord(recipe)
    }

    fun getSavedSearchList(): ArrayList<String> {
        return localDataSourceImpl.getSavedSearchWordList()
    }
}