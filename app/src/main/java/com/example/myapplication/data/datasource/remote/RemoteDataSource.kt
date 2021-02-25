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


    fun getRandomRecipesInFeed(
        success: (RecipeDTO.APIResponseRecipeList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getRandomRecipes("search","수배", 9)
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseRecipeList>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseRecipeList>,
                responseRecipe: Response<RecipeDTO.APIResponseRecipeList>
            ) {
                responseRecipe.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseRecipeList>, t: Throwable) {
                Log.e("/posts", "RandomRecipes 가져오기 실패 : " + t)
                fail(t)
            }
        })
    }

    fun getRandomRecipesInSearchFragment(
        randomCut: Int,
        success: (RecipeDTO.APIResponseRecipeList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getRandomRecipesInSearchFragment("search",randomCut-2,randomCut, 9)
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseRecipeList>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseRecipeList>,
                responseRecipe: Response<RecipeDTO.APIResponseRecipeList>
            ) {
                responseRecipe.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseRecipeList>, t: Throwable) {
                Log.e("/posts", "RandomRecipes 가져오기 실패 : " + t)
                fail(t)
            }
        })
    }

    fun getResultRecipesLatest(
        quertType: String,//"search" 많이 씀
        stepStart: Int?,
        stepEnd: Int?,
        time: Int?,
        startDate: String?,
        endDate: String?,
        order: String?,
        keyword: String?,
        limit:String?,
        offset: String?,
        success: (RecipeDTO.APIResponseRecipeList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getResultRecipesLatest(quertType,stepStart, stepEnd, time, startDate, endDate, order, keyword, limit, offset)
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseRecipeList>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseRecipeList>,
                responseRecipe: Response<RecipeDTO.APIResponseRecipeList>
            ) {
                responseRecipe.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseRecipeList>, t: Throwable) {
                Log.e("/posts", "RandomRecipes 가져오기 실패 : " + t)
                fail(t)
            }
        })
    }

    fun getRecipeById(
        recipeId: Int,
        success: (RecipeDTO.APIResponseRecipeData) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getRecipeById(recipeId)
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseRecipeData>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseRecipeData>,
                responseRecipe: Response<RecipeDTO.APIResponseRecipeData>
            ) {
                responseRecipe.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseRecipeData>, t: Throwable) {
                Log.e("/posts", "RandomRecipes 가져오기 실패 : " + t)
                fail(t)
            }
        })
    }

    fun getCommentsById(
        recipeId: Int,
        success: (RecipeDTO.APIResponseCommentList) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getCommentsById(recipeId)
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseCommentList>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseCommentList>,
                responseRecipe: Response<RecipeDTO.APIResponseCommentList>
            ) {
                responseRecipe.body()?.let{
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseCommentList>, t: Throwable) {
                Log.e("COMMENTS", "getComments 가져오기 실패 : " + t)
                fail(t)
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