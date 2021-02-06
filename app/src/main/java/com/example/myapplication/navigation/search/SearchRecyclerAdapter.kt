package com.example.myapplication.navigation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.list_random_recipe.view.*

class SearchRecyclerAdapter :
    RecyclerView.Adapter<SearchRecyclerAdapter.SearchRecyclderViewHolder>() {

    var randomRecipes = ArrayList<RecipeDTO.PostItem>()

    fun updateRandomRecipeList(timeLines: List<RecipeDTO.PostItem>) {
        this.randomRecipes.addAll(timeLines)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecyclderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_random_recipe,
            parent,
            false
        )
        return SearchRecyclderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRecyclderViewHolder, position: Int) {
        val data = randomRecipes.get(position)
        holder.bindItem(data)
    }

    override fun getItemCount(): Int {
        return randomRecipes.size
    }

    inner class SearchRecyclderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewRandomRecipe: ImageView = itemView.iv_random_recipe

        fun bindItem(data: RecipeDTO.PostItem) {
            data.imageUrl?.let {
                if (it.isNotEmpty()) {
                    val oneUrl = it[0]
                    Glide.with(App.instance)
                        .load(oneUrl)
                        .placeholder(R.drawable.ic_no_image)
                        .into(imageViewRandomRecipe);
                }
            }
        }
    }
}