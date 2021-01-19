package com.example.myapplication.data.repository

import com.example.myapplication.data.api.RecipeDTO

class RecipeRepositoryImpl : RecipeRepository(

){
    override fun getTimelineList(
        query: String,
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}