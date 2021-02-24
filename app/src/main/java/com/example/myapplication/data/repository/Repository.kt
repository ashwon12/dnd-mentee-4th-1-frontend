package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.local.LocalDataSource
import com.example.myapplication.data.datasource.remote.RemoteDataSource
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class Repository {

    private val localDataSourceImpl = LocalDataSource()
    private val remoteMovieDataSourceImpl = RemoteDataSource()

    fun getRandomRecipes(
        success: (RecipeDTO.APIResponseRecipeList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getRandomRecipes(
            success,
            fail
        )
    }

    fun getResultRecipes(
        word: String,
        success: (RecipeDTO.APIResponseRecipeList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getResultRecipes(
            word,
            success,
            fail
        )
    }

    fun getRecipeById(
        recipeId: Int,
        success: (RecipeDTO.APIResponseRecipeData) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getRecipeById(
            recipeId,
            success,
            fail
        )
    }

    fun getCommentsById(
        recipeId: Int,
        success: (RecipeDTO.APIResponseCommentList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getCommentsById(
            recipeId,
            success,
            fail
        )
    }

//    fun postTimeline(
//        postInfo: ArrayList<RecipeDTO.PostItem>,
//        success: (RecipeDTO.TimelineResponse) -> Unit,
//        fail: (Throwable) -> Unit
//    ) {
//        remoteMovieDataSourceImpl.postTimeline(
//            postInfo,
//            success,
//            fail
//        )
//    }

    fun saveSearchHistory(recipe: String) {
        localDataSourceImpl.saveSearchWord(recipe)
    }

    fun deleteSearchHistory(selected: String) {
        localDataSourceImpl.deleteSearcWord(selected)
    }

    fun getSavedSearchList(): ArrayList<String> {
        return localDataSourceImpl.getSavedSearchWordList()
    }


}