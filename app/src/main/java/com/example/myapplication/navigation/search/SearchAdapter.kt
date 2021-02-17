package com.example.myapplication.navigation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.detail.DetailFragment


class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    var randomRecipes = ArrayList<RecipeDTO.tempRandomRecipes>()

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_random_recipe,
                parent,
                false
            )
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = randomRecipes.get(position)
        holder.bindItem(data)

        holder.itemView.setOnClickListener {
            val activity = view.context as AppCompatActivity
            val detailFragment: Fragment = DetailFragment()
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, detailFragment)
                .addToBackStack(null)
                .commit();
        }
    }

    override fun getItemCount(): Int {
        return randomRecipes.size
    }


    fun updateRandomRecipeList(timeLines: ArrayList<RecipeDTO.tempRandomRecipes>) {
        this.randomRecipes.addAll(timeLines)
    }

}