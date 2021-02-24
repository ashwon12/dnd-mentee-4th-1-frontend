/**
 *  데이터 초기화 클래스
 */
package com.example.myapplication.data.datasource.remote.api

import android.net.Uri
import java.io.Serializable
import java.sql.Timestamp

class RecipeDTO {

    class PostItems : ArrayList<PostItem>()

    data class PostItem(
        val comment: List<String>?,
        val cookingTime: Any?,
        val cookingTool: Any?,
        val id: Int?,
        val imageUrl: List<String>?,
        val likeCount: Int?,
        val subTitle: String?,
        val title: String?
    )

    data class tempRandomRecipes(
        val id: Int?,
        val thunmbnail: String?,
        val title: String?,
        val ingredient: ArrayList<String>?,
        val subIngredient: ArrayList<String>?,
        val theme: ArrayList<String>?,
        //val steps: ArrayList<Image, Comment>,
        val starCount: Double?,
        val wishCount: Int?
//        val userProfile : String?,
//        val viewCount :Int?,
//        val writer: String?,
//        val writeDate: String?
    )


    data class tempResultRecipes(
        val id: Int?,
        val thunmbnail: String?,
        val title: String?,
        val ingredient: ArrayList<String>?,
        val subIngredient: ArrayList<String>?,
        val theme: ArrayList<String>?,
//        val steps: ArrayList<Image, Comment>,
        val starCount: Double?,
        val wishCount: Int?
//        val writer: User
    )

    data class UploadImage(
        val timestamp: String?,
        val status: String?,
        val error: String?,
        val message: String?,
        val path: String?,
        var data: String?
    )

    data class UploadRecipe(
        var title: String? = null,
        var description: String? = null,
        var thumbnail: String? = null,
        var mainIngredients: ArrayList<MainIngredients>?,
        var subIngredients: ArrayList<SubIngredients>?,
        var themeIds: ArrayList<Int>?,
        var steps: ArrayList<Steps>?,
        var time: String? = null,
        var pid : Int? = null,
        var viewCount: String? = null,
        var writerId: Int? = null
    )

    data class Timeline(
        val id: String,
        val title: String,
        val subTitle: String,
        val images: List<Recipe>? = null
    )

    data class Recipe(
        var number: String?,
        var comment: String?,
        var image: String?
    ) : Serializable

//    data class Filter(
//        var id: String,
//        var filterName: String
//    ) : Serializable

    data class Time(
        var timeName: String
    ) : Serializable


    // -------------- 2020. 2. 21 추가 -------------
    data class APIresponse(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val list: ArrayList<RecipeFinal>?
    )

    data class APIResponseList(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val list: ArrayList<RecipeFinal>? = null
    )

    data class APIResponseData(
        val timestamp: String?,
        val status: String?,
        val error: String?,
        val message: String?,
        val path: String?,
        val data: RecipeFinal?
    )

    data class RecipeFinal(
        var id: Int?,
        var title: String? = null,
        var description: String? = null,
        var thumbnail: String? = null,
        var mainIngredients: ArrayList<MainIngredients>?,
        var subIngredients: ArrayList<SubIngredients>?,
        var themes: ArrayList<Themes>?,
        var steps: ArrayList<Steps>?,
        var time: String? = null,
        var starCount: String? = null,// String -> Float 수정 필요
        var wishCount: String? = null,
        var viewCount: String? = null,
        var writer: Writer? = null,
        var pid : Int? = null
    )


    data class Writer(
        var name: String? = null,
        var id: Int?,
        var email: String? = null
    )

    data class Steps(
        var id: Int?,
        var description: String?,
        var imageUrl: String?,
        var sequence: String?
    )

    data class Themes(
        var id: Int?,
        var name: String?
    ) : Serializable

    data class MainIngredients (
        var name: String?
    )

    data class SubIngredients(
        var name: String?
    )

    data class Comment(
        val id: String?,
        val profilePic: String?,
        val nickname: String?,
        val date: String?,
        val comment: String?
    )

//    // 댓글
//    data class Comment(
//        val id: Int,
//        val recipeId: Int?,
//        val content: String? = null,
//        val imageUrl: String? = null,
//        val createDate: String? = null,
//        val modifiedDate: String? = null,
//        val user: User?
//    )

    data class User(
        val id: Int,
        val name: String,
        val email: String
    )
}


