package com.example.myapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApiModule {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(RecipeApi::class.java)
    val callGetTimelines = api.getTimelines("1")

    companion object {

        private const val BASE_URL = "https://yorijory.com"

    }
}