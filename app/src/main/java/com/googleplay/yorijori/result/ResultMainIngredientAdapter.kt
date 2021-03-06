package com.googleplay.yorijori.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO

class ResultMainIngredientAdapter :
    RecyclerView.Adapter<ResultMainIngredientAdapter.MainIngredientViewHolder>() {

    private lateinit var view: View

    var resultMainIngredients = ArrayList<RecipeDTO.MainIngredients>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainIngredientViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_result_main_ingredient,
                parent,
                false
            )
        return MainIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainIngredientViewHolder, position: Int) {
        holder.bind(resultMainIngredients[position], position)
    }

    override fun getItemCount(): Int {
        return resultMainIngredients.size
    }



    inner class MainIngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvMainIngredient = itemView.findViewById<TextView>(R.id.tv_description)

        fun bind(mainIgredient: RecipeDTO.MainIngredients, position: Int) {
            tvMainIngredient.text = mainIgredient.name
        }
    }

    fun updateMainIngredients(mainIngreds: ArrayList<RecipeDTO.MainIngredients>) {
        resultMainIngredients.clear()
        resultMainIngredients.addAll(mainIngreds)
    }
}