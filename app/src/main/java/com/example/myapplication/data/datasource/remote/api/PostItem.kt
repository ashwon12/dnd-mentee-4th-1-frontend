/**
 *  데이터 초기화 클래스
 */
package com.example.myapplication.data.datasource.remote.api

class PostItem : ArrayList<PostItem>(){
    data class PostItems(
        val comment: List<String>?,
        val cookingTime: Any?,
        val cookingTool: Any?,
        val id: Int,
        val imageUrl: List<String>,
        val likeCount: Int?,
        val subTitle: String,
        val title: String
    )

    data class Recipe(
        val number: String,
        var comment: String?,
        var image: String?
    )

    data class TimelineResponse(
        val display: Int,
        val items: List<PostItems>,
        val lastBuildDate: String,
        val start: Int,
        val total: Int
    )
}
