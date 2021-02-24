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

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageViewResultRecipe: ImageView = itemView.iv_result_image

    private val tvRecipeName: TextView = itemView.tv_recipe_name
    private val tv_description: TextView = itemView.tv_description

    private val tvRating: TextView = itemView.tv_star_rating
    private val tvViewCount: TextView = itemView.tv_viewcount
    private val ivProfileImage: ImageView = itemView.iv_result_profile
    private val tvNickname: TextView = itemView.tv_result_nickname
    private val tvCalendar: TextView = itemView.tv_calendar


    fun bindItem(data: RecipeDTO.RecipeFinal) {
        data.thumbnail?.let {
            if (it.isNotEmpty()) {
                Glide.with(App.instance)
                    .load(it)
                    .placeholder(R.drawable.ic_face)
                    .into(imageViewResultRecipe);
            }
        }

        tvRecipeName.text = data.title
        tv_description.text = data.description

        val floatRatingAvgRound = Math.round(data.starCount!! * 10) / 10f
        tvRating.text = floatRatingAvgRound.toString()
        tvViewCount.text = data.viewCount

        Glide.with(App.instance)
            .load(data.writer?.imageUrl)
            .placeholder(R.drawable.ic_face)
            .into(ivProfileImage);
        tvNickname.text = data.writer?.name
        tvCalendar.text = data.time
    }
}