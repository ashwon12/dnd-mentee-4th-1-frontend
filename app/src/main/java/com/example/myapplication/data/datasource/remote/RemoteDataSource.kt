package com.example.myapplication.data.datasource.remote

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.myapplication.App
import com.example.myapplication.data.datasource.remote.api.RecipeApi
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class RemoteDataSource {

    private val recipeApi = RecipeApi.create()

    fun getAllTimelinesFromRemote(
        success: (RecipeDTO.PostItems) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetAllTimelines = recipeApi.getAllTimelines()
        callGetAllTimelines.enqueue(object : retrofit2.Callback<RecipeDTO.PostItems> {
            override fun onResponse(
                call: Call<RecipeDTO.PostItems>,
                response: Response<RecipeDTO.PostItems>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                        val result: RecipeDTO.PostItems? = response.body()
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

    /** 홈에서 사용하는 api, queryType = viewTop ,labelTop **/
    fun getHomeRecipes(
        success: (RecipeDTO.APIResponseList) -> Unit,
        fail: (Throwable) -> Unit,
        queryType: String,
        order: String
    ) {
        val callHomeRecipes = recipeApi.getHomeRecipes(queryType,order)
        callHomeRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseList>{
                override fun onResponse(
                call: Call<RecipeDTO.APIResponseList>,
                response: Response<RecipeDTO.APIResponseList>
            ) {
                response.body()?.let {
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseList>, t: Throwable) {
                Log.e("queryType=viewTop", "홈 데이터 가져오기 실패 : " + t)
            }
        })
    }

    fun getMyRecipes(
        success: (RecipeDTO.APIResponseList) -> Unit,
        fail: (Throwable) -> Unit,
        queryType: String,
        token : String
    ) {
        val callMyRecipes = recipeApi.getMyRecipes(queryType,token)
        callMyRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIResponseList>{
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseList>,
                response: Response<RecipeDTO.APIResponseList>
            ) {
                response.body()?.let {
                    success(it)
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseList>, t: Throwable) {
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

    fun postImageUpload(
        imagePath: String,
        success: (RecipeDTO.UploadImage) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        var file = File(imagePath)
        val bitmap = BitmapFactory.decodeFile(imagePath)
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out)

        if (file.exists()) {
            Log.d("파일 존재", file.absolutePath)
        } else {
            Log.d("파일 없음", "상위 디렉토리 생성 ")
            file.mkdirs()
        }
        val requestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), out.toByteArray()
        )
        val body: MultipartBody.Part = MultipartBody.Part.createFormData(
            "data",
            file.name,
            requestBody
        )

        val callPostImageUpload = recipeApi.postImageUpload(body)
        callPostImageUpload.enqueue(object : Callback<RecipeDTO.UploadImage> {
            override fun onResponse(
                call: Call<RecipeDTO.UploadImage>,
                response: Response<RecipeDTO.UploadImage>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "이미지 업로드 성공!", Toast.LENGTH_SHORT).show()
                    // Log.d("image upload success!!1", response?.body().toString())
                    response.body()?.let {
                        success(it)
                    }
                } else {
                    Toast.makeText(App.instance, "실패...", Toast.LENGTH_SHORT).show()
                    Log.d("image upload fail....", response.message())
                    fail
                }
            }

            override fun onFailure(call: Call<RecipeDTO.UploadImage>, t: Throwable) {
                Log.d("image upload fail!!", t.message.toString())
            }
        })
    }

    fun postRecipeUpload(
        recipeInfo: RecipeDTO.UploadRecipe,
        success: (RecipeDTO.UploadRecipe) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callPostRecipeUpload = recipeApi.postRecipeUpload(recipeInfo)
        callPostRecipeUpload.enqueue(object : Callback<RecipeDTO.UploadRecipe> {
            override fun onResponse(
                call: Call<RecipeDTO.UploadRecipe>,
                response: Response<RecipeDTO.UploadRecipe>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "레시피 업로드 성공!", Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        success(it)
                    }
                } else {
                    Toast.makeText(App.instance, "실패...", Toast.LENGTH_SHORT).show()
                    Log.d("recipe upload fail....", response.message())
                    fail
                }
            }

            override fun onFailure(call: Call<RecipeDTO.UploadRecipe>, t: Throwable) {
                Log.d("recipe upload fail!!", t.message.toString())
            }
        })
    }

    fun postLoginInfo(
        token: String,
        email: RecipeDTO.RequestPostLogin,
        success: (RecipeDTO.RequestPostLogin) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callPostLoginInfo = recipeApi.postLoginInfo(token, email)
        callPostLoginInfo.enqueue(object : Callback<RecipeDTO.RequestPostLogin>{
            override fun onResponse(
                call: Call<RecipeDTO.RequestPostLogin>,
                response: Response<RecipeDTO.RequestPostLogin>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "로그인 전송 성공!", Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        success(it)
                    }
                } else {
                    Toast.makeText(App.instance, "실패...", Toast.LENGTH_SHORT).show()
                    fail
                }
            }

            override fun onFailure(call: Call<RecipeDTO.RequestPostLogin>, t: Throwable) {
            }
        })
    }

    fun postJoinInfo(
        token: String,
        joinInfo: RecipeDTO.RequestJoin,
        success: (RecipeDTO.RequestJoin) -> Unit,
        fail : (Throwable) -> Unit
    ){
        val callPostJoingInfo = recipeApi.postJoinInfo(token, joinInfo)
        callPostJoingInfo.enqueue(object: Callback<RecipeDTO.RequestJoin> {
            override fun onResponse(
                call: Call<RecipeDTO.RequestJoin>,
                response: Response<RecipeDTO.RequestJoin>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        success(it)
                    }

                } else {
                    Toast.makeText(App.instance, "실패...", Toast.LENGTH_SHORT).show()
                    Log.d("join fail....", response.message())
                    fail
                }
            }

            override fun onFailure(call: Call<RecipeDTO.RequestJoin>, t: Throwable) {

            }
        })
    }

    fun getFollower(
        token : String,
        success: (RecipeDTO.UserResponse) -> Unit,
        fail : (Throwable) -> Unit
    ){
        val callFollowerList = recipeApi.getFollowerList(token)
        callFollowerList.enqueue(object: Callback<RecipeDTO.UserResponse> {
            override fun onResponse(
                call: Call<RecipeDTO.UserResponse>,
                response: Response<RecipeDTO.UserResponse>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "팔로워 리스트 가져오기", Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        success(it)
                    }

                } else {
                    Log.d("팔로워 가져오기 실패", response.message())
                    fail
                }
            }

            override fun onFailure(call: Call<RecipeDTO.UserResponse>, t: Throwable) {

            }
        })
    }

    fun getFollowing(
        token : String,
        success: (RecipeDTO.UserResponse) -> Unit,
        fail : (Throwable) -> Unit
    ){
        val callFollowingList = recipeApi.getFollowingList(token)
        callFollowingList.enqueue(object: Callback<RecipeDTO.UserResponse> {
            override fun onResponse(
                call: Call<RecipeDTO.UserResponse>,
                response: Response<RecipeDTO.UserResponse>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "팔로잉 리스트 가져오기", Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        success(it)
                    }

                } else {
                    Log.d("팔로잉 리스트 가져오기 실패", response.message())
                    fail
                }
            }
            override fun onFailure(call: Call<RecipeDTO.UserResponse>, t: Throwable) {

            }
        })
    }

    fun getFollowingFeeds(
        token : String,
        success: (RecipeDTO.APIResponseList) -> Unit,
        fail : (Throwable) -> Unit
    ){
        val callfollowingFeedsList = recipeApi.getfollowingFeeds(token)
        callfollowingFeedsList.enqueue(object: Callback<RecipeDTO.APIResponseList> {
            override fun onResponse(
                call: Call<RecipeDTO.APIResponseList>,
                response: Response<RecipeDTO.APIResponseList>
            ) {
                if (response?.isSuccessful) {
                    Toast.makeText(App.instance, "피드 리스트 가져오기", Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        success(it)
                    }

                } else {
                    Log.d("피드 리스트 가져오기 실패", response.message())
                    fail
                }
            }
            override fun onFailure(call: Call<RecipeDTO.APIResponseList>, t: Throwable) {

            }
        })
    }

    fun deleteRecipe(
        recipeId: Int,
        success: (RecipeDTO.APIResponseData) -> Unit,
        fail: (Throwable) -> Unit
    ){
        val calldeleteRecipes = recipeApi.deleteRecipe(recipeId)

    }

}