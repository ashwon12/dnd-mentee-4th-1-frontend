package com.example.myapplication.data.datasource.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.myapplication.data.datasource.remote.api.RecipeApi
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import retrofit2.Call
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {

    private val recipeApi = RecipeApi.create()

    override fun getAllTimelinesFromRemote(
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetAllTimelines = recipeApi.getAllTimelines()
        callGetAllTimelines.enqueue(object : retrofit2.Callback<RecipeDTO.TimelineResponse>{
            override fun onResponse(
                call: Call<RecipeDTO.TimelineResponse>,
                response: Response<RecipeDTO.TimelineResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                        Log.d(TAG, "성공 : ${response.raw()}")
                    }
                }
            }
            override fun onFailure(call: Call<RecipeDTO.TimelineResponse>, t: Throwable) {
                Log.d("/posts", "getAlltimelinesFromRemote 실패 : " + t)
            }
        })

    }

    override fun postTimeline(
        postInfo: ArrayList<RecipeDTO.Timeline>,
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {

    }
}