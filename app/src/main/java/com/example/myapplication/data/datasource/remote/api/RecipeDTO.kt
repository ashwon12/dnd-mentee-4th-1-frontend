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

    data class UploadImage(
        val timestamp: String?,
        val status: String?,
        val error: String?,
        val message: String?,
        val path: String?,
        var data: String?
    )

    data class userFollow(
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

    data class RequestPostLogin(
        var email: String?,
        var data: String?
    )

    data class RequestJoin(
        var email: String? = null,
        var name: String? = null,
        var imageUrl: String? = null,
        var data: String? = null
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


    /**    2. 24.(수) 이후 수정   **/

    // Recipe List로 받을 때
    data class APIResponseRecipeList(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val list: ArrayList<RecipeFinal>? = null
    )

    //Recipe 하나만 받을 때
    data class APIResponseRecipeData(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val data: RecipeFinal? = null
    )

    data class UserResponse(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val list: ArrayList<User>? = null
    )

    // Comment List로 받을 때
    data class APIResponseCommentList(
        val timestamp: String,
        val status: String,
        val error: String,
        val message: String,
        val path: String,
        val list: ArrayList<Comment>? = null
    )

    data class RecipeFinal(
        var id: Int,
        var title: String? = null,
        var description: String? = null,
        var thumbnail: String? = null,
        var mainIngredients: ArrayList<MainIngredients>,
        var subIngredients: ArrayList<SubIngredients>,
        var themes: ArrayList<Themes>,
        var steps: ArrayList<Steps>,
        var time: String? = null,
        var starCount: Double? = null,// String -> Float 수정 필요
        var wishCount: String? = null,
        var viewCount: String? = null,
        var writer: Writer? = null,
        var createdDate: String?
    )

    data class Writer(
        var name: String? = null,
        var id: Int,
        var email: String? = null,
        val imageUrl: String? = null
    )

    data class Steps(
        var id: Int?,
        var description: String?,
        var imageUrl: String?,
        var sequence: String?
    )

    data class Themes(
        var id: Int,
        var name: String?
    ) : Serializable

    data class MainIngredients (
        var name: String?
    )

    data class SubIngredients(
        var name: String?
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

//    class MainIngredients {
//        var name: String? = null
//    }
//
//    class SubIngredients {
//        var name: String? = null
//    }


    // 댓글
    data class Comment(
        val createdDate: String? = null,
        val modifiedDate: String? = null,
        val id: Int,
        val content: String? = null,
        val imageUrl: String? = null,
        val writer: Writer?,
        val pid: Int?
    )

    data class User(
        val email: String,
        val id: Int,
        val imgUrl : String,
        val name: String
    )
}


