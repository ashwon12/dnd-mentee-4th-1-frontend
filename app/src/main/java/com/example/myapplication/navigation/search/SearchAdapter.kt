package com.example.myapplication.navigation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    var randomRecipes = ArrayList<RecipeDTO.PostItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_random_recipe,
                parent,
                false
            )
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = randomRecipes.get(position)
        holder.bindItem(data)
    }

    override fun getItemCount(): Int {
        return randomRecipes.size
    }


    fun updateRandomRecipeList(timeLines: List<RecipeDTO.PostItem>) {
        this.randomRecipes.addAll(timeLines)
    }

}