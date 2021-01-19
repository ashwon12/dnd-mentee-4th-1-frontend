package com.example.myapplication.data.datasource.remote.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("v1/search/timeline.json")
    fun getAllTimelines(
        @Query("id") id: String? = null
    ): Call<RecipeDTO.TimelineResponse>

    companion object {
        private const val BASE_URL = "https://yorijory.com"

        fun create(): RecipeApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApi::class.java)
        }
    }

}