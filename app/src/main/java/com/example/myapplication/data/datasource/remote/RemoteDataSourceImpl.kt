package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.datasource.remote.api.RecipeApi
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import retrofit2.Call
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {

    private val recipeApi = RecipeApi.create()

    override fun getAllTimelinesFromRemote(
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        recipeApi.getAllTimelines()

    }
}