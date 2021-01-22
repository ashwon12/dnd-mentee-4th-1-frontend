/**
 *  데이터 초기화 클래스
 */
package com.example.myapplication.data.datasource.remote.api

class RecipeDTO {

    class PostItem : ArrayList<TimelineResponse>()

    data class PostItems(
        val comment: List<String>,
        val cookingTime: Any,
        val cookingTool: Any,
        val id: Int,
        val imageUrl: List<String>,
        val likeCount: Int,
        val subTitle: String,
        val title: String
    )

    data class Timeline(
        val id: String,
        val title: String,
        val subTitle: String,
        val images: List<Recipe>? = null
    )

    data class Recipe(
        val number: String,
        var comment: String?,
        var image: String?
    )

    data class TimelineResponse(
        val comment: ArrayList<String>?,
        val cookingTime: Any?,
        val cookingTool: Any?,
        val id: Int?,
        val imageUrl: ArrayList<String>?,
        val likeCount: Int?,
        val subTitle: String?,
        val title: String?
    )
}
