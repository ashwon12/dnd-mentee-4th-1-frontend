package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.remote.api.RecipeDTO

interface Repository {
    fun getAllTimelineList(
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}