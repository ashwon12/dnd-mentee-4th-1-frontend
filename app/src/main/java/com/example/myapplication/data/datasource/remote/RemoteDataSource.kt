package com.example.myapplication.data.datasource.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.myapplication.data.datasource.remote.api.RecipeApi
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import retrofit2.Call
import retrofit2.Response

class RemoteDataSource {

    private val recipeApi = RecipeApi.create()

    fun getAllTimelinesFromRemote(
        success: (RecipeDTO.PostItems) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetAllTimelines = recipeApi.getAllTimelines()
        callGetAllTimelines.enqueue(object : retrofit2.Callback<RecipeDTO.PostItems>{
            override fun onResponse(
                call: Call<RecipeDTO.PostItems>,
                response: Response<RecipeDTO.PostItems>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                        val result : RecipeDTO.PostItems? = response.body()
                        Log.d(TAG, "성공 : ${response.raw()}")
                        Log.d("result", result?.get(0)?.title.toString())
                    }
                }
            }
            override fun onFailure(call: Call<RecipeDTO.PostItems>, t: Throwable) {
                Log.e("/posts", "AlltimelinesFromRemote 가져오기 실패 : " + t)
            }
        })

    }

    fun getRandomRecipes(
        success: (RecipeDTO.APIResponseList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getRandomRecipes("search","수배")
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseList>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseList>,
                response: Response<RecipeDTO.APIResponseList>
            ) {
                response.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseList>, t: Throwable) {
                Log.e("/posts", "RandomRecipes 가져오기 실패 : " + t)
                fail(t)
            }
        })
    }

    fun getRecipeById(
        recipeId: Int,
        success: (RecipeDTO.APIResponseData) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getRecipeById(recipeId)
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseData>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseData>,
                response: Response<RecipeDTO.APIResponseData>
            ) {
                response.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseData>, t: Throwable) {
                Log.e("/posts", "RandomRecipes 가져오기 실패 : " + t)
                fail(t)
            }
        })
    }

    /** 홈에서 사용하는 api, queryType = viewTop ,labelTop **/
    fun getHomeRecipes(
        success: (RecipeDTO.APIresponse) -> Unit,
        fail: (Throwable) -> Unit,
        queryType: String,
        order : String
    ) {
        val callHomeRecipes = recipeApi.getHomeRecipes(queryType,order)
        callHomeRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIresponse>{
                override fun onResponse(
                call: Call<RecipeDTO.APIresponse>,
                response: Response<RecipeDTO.APIresponse>
            ) {
                response.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIresponse>, t: Throwable) {
                Log.e("queryType=viewTop", "홈 데이터 가져오기 실패 : " + t)
            }
        })
    }

//    fun postTimeline(
//        postInfo: ArrayList<RecipeDTO.PostItem>,
//        success: (RecipeDTO.TimelineResponse) -> Unit,
//        fail: (Throwable) -> Unit
//    ) {
//
//    }
}