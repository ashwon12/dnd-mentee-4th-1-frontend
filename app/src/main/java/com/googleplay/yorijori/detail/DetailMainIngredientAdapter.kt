package com.example.yorijori.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.R
import com.example.yorijori.data.datasource.remote.api.RecipeDTO

class DetailMainIngredientAdapter :
    RecyclerView.Adapter<DetailMainIngredientAdapter.MainIngredientViewHolder>() {

    private lateinit var view: View

    var mainIngredients = ArrayList<RecipeDTO.MainIngredients>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainIngredientViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_main_ingredient,
                parent,
                false
            )
        return MainIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainIngredientViewHolder, position: Int) {
        holder.bind(mainIngredients[position], position)
    }

    override fun getItemCount(): Int {
        return mainIngredients.size
    }



    inner class MainIngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvMainIngredient = itemView.findViewById<TextView>(R.id.tv_main_ingredient)

        fun bind(mainIgredient: RecipeDTO.MainIngredients, position: Int) {
            tvMainIngredient.text = mainIgredient.name
        }
    }

    fun updateMainIngredients(mainIngreds: ArrayList<RecipeDTO.MainIngredients>) {
        mainIngredients.clear()
        mainIngredients.addAll(mainIngreds)
    }
}