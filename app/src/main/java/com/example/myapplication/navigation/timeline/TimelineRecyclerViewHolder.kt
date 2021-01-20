/**
 *  받아온 데이터를 구현한 UI의 View에 넣어주는 클래스
 */
package com.example.myapplication.navigation.timeline

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class TimelineRecyclerViewHolder(v : View) : RecyclerView.ViewHolder(v) {

    private val title = v.findViewById<TextView>(R.id.tv_title)
    private val subtitle = v.findViewById<TextView>(R.id.tv_subtitle)
    private val image = v.findViewById<ImageView>(R.id.iv_image)


    fun bind(data : RecipeDTO.Timeline){
        title.text = data.title
        subtitle.text = data.subTitle
        Glide.with(App.instance).load(data.imageUrl[0]).into(image);
    }
}

