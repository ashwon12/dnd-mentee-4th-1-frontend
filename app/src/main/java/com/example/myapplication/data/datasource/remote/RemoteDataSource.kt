package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.datasource.remote.api.PostItem

interface RemoteDataSource {
    fun getAllTimelinesFromRemote(
        success: (PostItem.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )

    fun postTimeline(
        postInfo: ArrayList<PostItem.PostItems>,
        success: (PostItem.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}