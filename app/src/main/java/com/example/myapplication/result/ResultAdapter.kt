package com.example.myapplication.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class ResultAdapter : RecyclerView.Adapter<ResultViewHolder>() {

    var resultRecipes = ArrayList<RecipeDTO.tempResultRecipes>()

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_result_recipe,
                parent,
                false
            )
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val data = resultRecipes.get(position)
        holder.bindItem(data)
    }

    override fun getItemCount(): Int {
        return resultRecipes.size
    }

    fun addSampleResult(data: RecipeDTO.tempResultRecipes) {
        resultRecipes.add(data)
    }
}