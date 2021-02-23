package com.example.myapplication.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class DetailSubIngredientAdapter :
    RecyclerView.Adapter<DetailSubIngredientAdapter.SubIngredientViewHolder>() {

    private lateinit var view: View

    var subIngredients = ArrayList<RecipeDTO.SubIngredients>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubIngredientViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_main_ingredient,
                parent,
                false
            )
        return SubIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubIngredientViewHolder, position: Int) {
        holder.bind(subIngredients[position], position)
    }

    override fun getItemCount(): Int {
        return subIngredients.size
    }


    inner class SubIngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvMainIngredient = itemView.findViewById<TextView>(R.id.tv_main_ingredient)

        fun bind(mainIgredient: RecipeDTO.SubIngredients, position: Int) {
            tvMainIngredient.text = mainIgredient.name
        }
    }

    fun updateMainIngredients(subIngreds: ArrayList<RecipeDTO.SubIngredients>) {
        subIngredients = subIngreds
    }
}