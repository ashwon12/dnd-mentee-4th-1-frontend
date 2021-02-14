package com.example.myapplication.navigation.search

import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.detail.DetailRecipeActivity
import android.util.Pair
import androidx.core.content.ContextCompat.startActivity

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    var randomRecipes = ArrayList<RecipeDTO.tempRandomRecipes>()

    private lateinit var view: View
    private val mainActivity = MainActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        view = LayoutInflater
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

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailRecipeActivity::class.java)

            val imageView: ImageView = view.findViewById<ImageView>(R.id.iv_random_recipe)
            val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)
            pairs[0] = Pair<View, String>(imageView, "@string/transition_random_to_detail")

            context.startActivity(intent)
           //context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity, pairs[0]).toBundle());
        }
    }

    override fun getItemCount(): Int {
        return randomRecipes.size
    }


    fun updateRandomRecipeList(timeLines: ArrayList<RecipeDTO.tempRandomRecipes>) {
        this.randomRecipes.addAll(timeLines)
    }

}