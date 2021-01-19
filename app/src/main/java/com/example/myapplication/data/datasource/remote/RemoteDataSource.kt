package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.datasource.remote.api.RecipeDTO

interface RemoteDataSource {
    fun getAllTimelinesFromRemote(
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}