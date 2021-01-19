package com.example.myapplication.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("v1/search/timeline.json")
    fun getTimeliness(
        @Query("query") query: String
    ):Call<RecipeDTO.TimelineResponse>

}