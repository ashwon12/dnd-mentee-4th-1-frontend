package com.example.myapplication.navigation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.detail.DetailFragment


class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    var randomRecipes = ArrayList<RecipeDTO.RecipeFinal>()

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

            val itemImageView = view.findViewById<ImageView>(R.id.iv_random_recipe)
            ViewCompat.setTransitionName(itemImageView, "@string/transition_random_to_detail")

            val activity = view.context as AppCompatActivity
            val detailFragment: Fragment = DetailFragment()

            val manager: FragmentManager = activity.supportFragmentManager
            manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .setReorderingAllowed(true)
                .addSharedElement(itemImageView, "@string/transition_random_to_detail")
                .replace(R.id.fl_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return randomRecipes.size
    }


    fun updateRandomRecipeList(timeLines: ArrayList<RecipeDTO.RecipeFinal>) {
        this.randomRecipes.addAll(timeLines)
    }

}