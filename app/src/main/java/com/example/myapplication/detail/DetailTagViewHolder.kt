package com.example.myapplication.detail

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class DetailTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val btnTag = itemView.findViewById<Button>(R.id.btn_tag_button)

    fun bind(data: String) {

    }

    fun bindMain(data: RecipeDTO.MainIngredients) {
        btnTag.text = data.name
    }

    fun bindSub(data: RecipeDTO.SubIngredients) {
        btnTag.text = data.name
    }

    fun bindTheme(data: RecipeDTO.Themes) {
        btnTag.text = data.name
    }

}