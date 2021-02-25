package com.example.myapplication.data.repository

import android.net.Uri
import com.example.myapplication.data.datasource.local.LocalDataSource
import com.example.myapplication.data.datasource.remote.RemoteDataSource
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import okhttp3.MultipartBody
import org.json.JSONObject

class Repository {

    private val localDataSourceImpl = LocalDataSource()
    private val remoteMovieDataSourceImpl = RemoteDataSource()

    fun getRandomRecipes(
        success: (RecipeDTO.APIResponseList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getRandomRecipes(
            success,
            fail
        )
    }


    fun getRecipeById(
        recipeId: Int,
        success: (RecipeDTO.APIResponseData) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getRecipeById(
            recipeId,
            success,
            fail
        )
    }


    fun getHomeRecipes(
        success: (RecipeDTO.APIResponseList) -> Unit,
        fail: (Throwable) -> Unit,
        queryType: String,
        order : String
    ) {
        remoteMovieDataSourceImpl.getHomeRecipes(
            success,
            fail,
            queryType,
            order
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



    // 이미지 s3 업로드

    fun postImageUpload(
        imagePath: String,
        success: (RecipeDTO.UploadImage) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.postImageUpload(imagePath, success, fail)
    }

    // 레시피 등록

    fun postRecipeUpload(
        recipeInfo: (RecipeDTO.UploadRecipe),

        success: (RecipeDTO.UploadRecipe) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.postRecipeUpload(recipeInfo, success, fail)
    }
}