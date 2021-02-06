package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.local.LocalDataSourceImpl
import com.example.myapplication.data.datasource.remote.RemoteDataSourceImpl
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class RepositoryImpl : Repository {

    private val localDataSourceImpl = LocalDataSourceImpl()
    private val remoteMovieDataSourceImpl = RemoteDataSourceImpl()

    override fun getAllTimelineList(
        success: (RecipeDTO.PostItems) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.getAllTimelinesFromRemote(
            success,
            fail
        )
    }

    override fun postTimeline(
        postInfo: ArrayList<RecipeDTO.PostItem>,
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        remoteMovieDataSourceImpl.postTimeline(
            postInfo,
            success,
            fail
        )
    }

    override fun saveSearch(recipe: String) {
        localDataSourceImpl.saveSearch(recipe)
    }

    override fun getSavedSearchList(): ArrayList<String> {
        return localDataSourceImpl.getSavedSearchList()
    }
}