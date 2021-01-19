package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.remote.RemoteDataSourceImpl
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class RecipeRepositoryImpl : Repository {

    private val remoteMovieDataSourceImp = RemoteDataSourceImpl()

    override fun getAllTimelineList(
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImp.getAllTimelinesFromRemote(
            success,
            fail
        )
    }
}