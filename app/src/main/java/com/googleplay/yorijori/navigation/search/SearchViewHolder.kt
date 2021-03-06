package com.googleplay.yorijori.navigation.search

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.googleplay.yorijori.App
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.item_random_recipe.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageViewRandomRecipe: ImageView = itemView.iv_random_recipe

    fun bindItem(data: RecipeDTO.RecipeFinal) {
        data.thumbnail?.let {
            if (it.isNotEmpty()) {
                Glide.with(App.instance)
                    .load(data.thumbnail)
                    .placeholder(R.drawable.ic_face)
                    .into(imageViewRandomRecipe);
            }
        }
    }
}