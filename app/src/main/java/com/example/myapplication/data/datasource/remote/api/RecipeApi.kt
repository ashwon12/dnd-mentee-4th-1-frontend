package com.example.myapplication.data.datasource.remote.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface RecipeApi {

    @GET("/posts")
    fun getAllTimelines(
        @Query("id") id: String? = null
    ): Call<RecipeDTO.PostItems>

    @GET("/recipes")
    fun getRandomRecipes(
        @Query("queryType") queryType: String,
        @Query("keyword") keyword: String
    ): Call<RecipeDTO.RecipeFinal>

    @FormUrlEncoded
    @POST("/posts")
    fun postTimeline(
        @Field("id") id: String,
        @Field("title") title: String,
        @Field("subTitle") subTitle: String,
        @Field("imageUrl") imageUrls: List<String>,
        @Field("comment") comments: List<String>
    ): Call<RecipeDTO.PostItems>

    @Multipart
    @POST("/upload/step")
    fun postImageUpload(
        @Part imageFile : MultipartBody.Part
    ): Call<RecipeDTO.UploadImage>

    @FormUrlEncoded
    @POST("/recipes")
    fun postRecipeUpload(
        @FieldMap recipe : HashMap<String, Any>
    ): Call<RecipeDTO.RecipeFinal>


    companion object {
        private const val BASE_URL = "http://13.209.68.130:8080"

        fun create(): RecipeApi {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val gson : Gson =  GsonBuilder()
                .setLenient()
                .create()

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(RecipeApi::class.java)
        }
    }

}