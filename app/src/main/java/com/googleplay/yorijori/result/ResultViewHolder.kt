package com.googleplay.yorijori.result

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.googleplay.yorijori.App
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.detail.ResultMainIngredientAdapter
import kotlinx.android.synthetic.main.item_result_recipe.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageViewResultRecipe: ImageView = itemView.iv_result_image

    private lateinit var resultMainIngredientAdapter: ResultMainIngredientAdapter

    private val tvRecipeName: TextView = itemView.tv_recipe_name
    private val rvMainIngredientInItem = itemView.findViewById<RecyclerView>(R.id.rv_result_main_ingredient)
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
        setMainIngredientRecyclerView(data.mainIngredients)

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

    private fun setMainIngredientRecyclerView(mainIngredients: ArrayList<RecipeDTO.MainIngredients>) {
        resultMainIngredientAdapter = ResultMainIngredientAdapter()
        rvMainIngredientInItem.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        rvMainIngredientInItem.setHasFixedSize(true)
        rvMainIngredientInItem.adapter = resultMainIngredientAdapter
        resultMainIngredientAdapter.resultMainIngredients = mainIngredients
    }
}