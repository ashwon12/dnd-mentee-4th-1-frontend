package com.example.myapplication.data.repository

import com.example.myapplication.data.api.RecipeDTO

interface RecipeRepository {
    fun getTimelineList(
        query: String,
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}