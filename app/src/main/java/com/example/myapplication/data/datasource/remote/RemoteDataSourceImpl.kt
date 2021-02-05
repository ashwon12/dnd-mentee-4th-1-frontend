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
        success: (RecipeDTO.PostItem) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetAllTimelines = recipeApi.getAllTimelines()
        callGetAllTimelines.enqueue(object : retrofit2.Callback<RecipeDTO.PostItem>{
            override fun onResponse(
                call: Call<RecipeDTO.PostItem>,
                response: Response<RecipeDTO.PostItem>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                        val result : RecipeDTO.PostItem? = response.body()
                        Log.d(TAG, "성공 : ${response.raw()}")
                        Log.d("result", result?.get(0)?.title.toString())
                    }
                }
            }
            override fun onFailure(call: Call<RecipeDTO.PostItem>, t: Throwable) {
                Log.d("/posts", "getAlltimelinesFromRemote 실패 : " + t)
            }
        })

    }

    override fun postTimeline(
        postInfo: ArrayList<RecipeDTO.PostItems>,
        success: (RecipeDTO.TimelineResponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {

    }
}