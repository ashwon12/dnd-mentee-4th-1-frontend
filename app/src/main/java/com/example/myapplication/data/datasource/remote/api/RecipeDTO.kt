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

    data class Comment(
        val id: String,
        val profilePic: String?,
        val nickname: String,
        val date: String,
        val comment: String?
    )

    // -------------- 2020. 2. 21 추가 -------------
    data class APIresponse(
        val timestamp: String,
        val status: String,
        val error:String,
        val message: String,
        val path: String,
        val list: ArrayList<RecipeFinal>? = null
    )

    data class RecipeFinal(
        var id: Int,
        var title: String? = null,
        var thumbnail: String? = null,
        var mainIngredients: Array<MainIngredients>,
        var subIngredients: Array<String>,
        var themes: Array<Themes>,
        var steps: Array<Steps>,
        var time: String? = null,
        var starCount: String? = null,
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
        var sequence: String,
        var imageUrl: String? = null,
        var description: String
    )

    class Themes(
        var name: String? = null,
        var id: Int
    )

    class MainIngredients {
        var name: String? = null
    }
}


