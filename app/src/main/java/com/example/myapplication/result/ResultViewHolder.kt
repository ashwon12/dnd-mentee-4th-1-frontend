package com.example.myapplication.result

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.item_result_recipe.view.*

class ResultViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    private val imageViewResultRecipe: ImageView = itemView.iv_result_image
    private val tvRecipeName: TextView = itemView.tv_recipe_name
    private val tvRating: TextView = itemView.tv_star_rating
    private val ivProfileImage :ImageView = itemView.iv_result_profile
    private val tvNickname: TextView = itemView.tv_result_nickname


    fun bindItem(data: RecipeDTO.tempResultRecipes) {
        data.thunmbnail?.let {
            if (it.isNotEmpty()) {
                Glide.with(App.instance)
                    .load(data.thunmbnail)
                    .placeholder(R.drawable.ic_face)
                    .into(imageViewResultRecipe);
            }
        }

        tvRecipeName.text = data.title
    }
}