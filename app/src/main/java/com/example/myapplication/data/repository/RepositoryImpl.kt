package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.remote.RemoteDataSourceImpl
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class RepositoryImpl : Repository {

    private val remoteMovieDataSourceImp = RemoteDataSourceImpl()

    override fun getAllTimelineList(
        success: (RecipeDTO.PostItems) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImp.getAllTimelinesFromRemote(
            success,
            fail
        )
    }

    override fun postTimeline(
        postInfo: ArrayList<RecipeDTO.PostItem>,
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImp.postTimeline(
            postInfo,
            success,
            fail
        )
    }
}