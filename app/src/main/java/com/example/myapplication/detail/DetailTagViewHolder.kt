package com.example.myapplication.detail

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class DetailTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTag = itemView.findViewById<TextView>(R.id.btn_tag_button)


    fun bind(tagText: RecipeDTO.Themes) {
        tvTag.text = tagText.name
    }

}