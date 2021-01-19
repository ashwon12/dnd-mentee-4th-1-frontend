/**
 *  받아온 데이터를 구현한 UI의 View에 넣어주는 클래스
 */
package com.example.myapplication.navigation.timeline

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.RecipeDTO

class TimelineRecyclerViewHolder(v : View) : RecyclerView.ViewHolder(v) {

    private val title = v.findViewById<TextView>(R.id.tv_title)
    private val subtitle = v.findViewById<TextView>(R.id.tv_subtitle)

    fun bind(data : RecipeDTO.Timeline){
        title.text = data.title
        subtitle.text = data.subTitle
    }
}