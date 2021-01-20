/**
 *  데이터 초기화 클래스
 */
package com.example.myapplication.data.datasource.remote.api

class RecipeDTO{
    data class Timeline(
        val id: String,
        val title: String,
        val subTitle: String,
        val imageUrl: List<String>
    )

    data class Recipe(
        val number: String,
        var comment: String?,
        var image: String?
    )

    data class TimelineResponse(
        val display: Int,
        val items: List<Timeline>,
        val lastBuildDate: String,
        val start: Int,
        val total: Int
    )
}
