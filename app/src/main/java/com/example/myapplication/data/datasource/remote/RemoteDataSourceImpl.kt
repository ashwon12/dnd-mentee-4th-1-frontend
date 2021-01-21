package com.example.myapplication.data.datasource.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.myapplication.data.datasource.remote.api.RecipeApi
import com.example.myapplication.data.datasource.remote.api.PostItem
import retrofit2.Call
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {

    private val recipeApi = RecipeApi.create()

    override fun getAllTimelinesFromRemote(
        success: (PostItem.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetAllTimelines = recipeApi.getAllTimelines()
        callGetAllTimelines.enqueue(object : retrofit2.Callback<PostItem.TimelineResponse>{
            override fun onResponse(
                call: Call<PostItem.TimelineResponse>,
                response: Response<PostItem.TimelineResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                        Log.d(TAG, "성공 : ${response.raw()}")
                    }
                }
            }
            override fun onFailure(call: Call<PostItem.TimelineResponse>, t: Throwable) {
                Log.d("/posts", "getAlltimelinesFromRemote 실패 : " + t)
            }
        })

    }

    override fun postTimeline(
        postInfo: ArrayList<PostItem.PostItems>,
        success: (PostItem.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {

    }
}