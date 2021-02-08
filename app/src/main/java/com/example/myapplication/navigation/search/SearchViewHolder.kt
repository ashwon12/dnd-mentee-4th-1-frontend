package com.example.myapplication.navigation.search

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.list_random_recipe.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageViewRandomRecipe: ImageView = itemView.iv_random_recipe

    fun bindItem(data: RecipeDTO.PostItem) {
        data.imageUrl?.let {
            if (it.isNotEmpty()) {
                val oneUrl = it[0]
                Glide.with(App.instance)
                    .load(oneUrl)
                    .placeholder(R.drawable.ic_face)
                    .into(imageViewRandomRecipe);
            }
        }
    }
}