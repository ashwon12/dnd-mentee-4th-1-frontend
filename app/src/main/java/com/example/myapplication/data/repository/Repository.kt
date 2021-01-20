package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.remote.api.PostItem

interface Repository {
    fun getAllTimelineList(
        success: (PostItem.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
    fun postTimeline(
        postInfo: ArrayList<PostItem.PostItems>,
        success: (PostItem.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    )
}