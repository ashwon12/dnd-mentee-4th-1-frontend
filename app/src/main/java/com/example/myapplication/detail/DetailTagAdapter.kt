package com.example.myapplication.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class DetailTagAdapter : RecyclerView.Adapter<DetailTagViewHolder>() {

    private lateinit var view: View

    var tags = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTagViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.tag_button_item,
                parent,
                false
            )
        return DetailTagViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailTagViewHolder, position: Int) {
        val data = tags.get(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun updateMainIngredients(mainIngs: ArrayList<RecipeDTO.MainIngredients>) {
        for (ingredient in mainIngs) {
            tags.add(ingredient.name!!)
        }
    }

    fun updateSubIngredients(subIngs: ArrayList<RecipeDTO.SubIngredients>) {
        for (ingredient in subIngs) {
            tags.add(ingredient.name!!)
        }
    }

    fun updateThemes(themes: ArrayList<RecipeDTO.Themes>) {
        for (theme in themes) {
            tags.add(theme.name!!)
        }
    }
}