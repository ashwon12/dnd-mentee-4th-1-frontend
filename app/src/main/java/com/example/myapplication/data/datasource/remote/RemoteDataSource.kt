package com.example.myapplication.data.datasource.remote

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.myapplication.App
import com.example.myapplication.data.datasource.remote.api.RecipeApi
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
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

    fun getRandomRecipes(
        success: (RecipeDTO.APIresponse) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        val callGetRandomRecipes = recipeApi.getRandomRecipes("search", "수배")
        callGetRandomRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIresponse> {
            override fun onResponse(
                call: Call<RecipeDTO.APIresponse>,
                response: Response<RecipeDTO.APIresponse>
            ) {
                response.body()?.let {
                    success(it)
                }
            }

            override fun onFailure(call: Call<RecipeDTO.APIresponse>, t: Throwable) {
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
        order: String
    ) {
        val callHomeRecipes = recipeApi.getHomeRecipes(queryType, order)
        callHomeRecipes.enqueue(object : retrofit2.Callback<RecipeDTO.APIresponse> {
            override fun onResponse(
                call: Call<RecipeDTO.APIresponse>,
                response: Response<RecipeDTO.APIresponse>
            ) {
                response.body()?.let {
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

    fun postImageUpload(
        imagePath: String,
        success: (RecipeDTO.UploadImage) -> Unit,
        fail: (Throwable) -> Unit
    ) {
        var file = File(imagePath)
        val bitmap = BitmapFactory.decodeFile(imagePath)
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out)

        Log.d("fileName", file.name + " " + "여긴데1")
        Log.d("fileName", file.path + " " + "여긴데2")
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
}