/**
 *  데이터 초기화 클래스
 */
package com.example.myapplication.data.datasource.remote.api

import java.io.Serializable

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

    data class Filter(
        var filterName: String
    ) : Serializable

    data class Time(
        var timeName: String
    ) : Serializable

    // -------------- 2020. 2. 21 추가 -------------
    data class APIResponseList(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val list: ArrayList<RecipeFinal>? = null
    )

    data class APIResponseData(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val data: RecipeFinal? = null
    )

    data class RecipeFinal(
        var id: Int,
        var title: String? = null,
        var thumbnail: String? = null,
        var mainIngredients: ArrayList<MainIngredients>,
        var subIngredients: ArrayList<SubIngredients>,
        var themes: ArrayList<Themes>,
        var steps: ArrayList<Steps>,
        var time: String? = null,
        var starCount: String? = null,// String -> Float 수정 필요
        var wishCount: String? = null,
        var viewCount: String? = null,
        var writer: Writer? = null
    )

    class Writer(
        var name: String? = null,
        var id: Int,
        var email: String? = null
    )

    class Steps(
        var id: Int,
        var description: String,
        var imageUrl: String? = null,
        var sequence: String
    )

    class Themes(
        var name: String? = null,
        var id: Int
    )

    class MainIngredients {
        var name: String? = null
    }

    class SubIngredients {
        var name: String? = null
    }


    // 댓글
    data class Comment(
        val id: Int,
        val recipeId: Int?,
        val content: String? = null,
        val imageUrl: String? = null,
        val createDate: String? = null,
        val modifiedDate: String? = null,
        val user: User?
    )

    data class User(
        val id: Int,
        val name: String,
        val email: String
    )
}


