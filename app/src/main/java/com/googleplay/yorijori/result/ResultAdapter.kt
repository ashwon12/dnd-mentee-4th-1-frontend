package com.googleplay.yorijori.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.detail.DetailFragment

class ResultAdapter : RecyclerView.Adapter<ResultViewHolder>() {

    var resultRecipes = ArrayList<RecipeDTO.RecipeFinal>()

    var keyword: String = ""

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

        holder.itemView.setOnClickListener {

            val itemImageView = view.findViewById<ImageView>(R.id.iv_result_image)
            ViewCompat.setTransitionName(itemImageView, "@string/transition_random_to_detail")

            val args = Bundle()// 클릭된 Recipe의 id 전달
            args.putInt("recipeId", data.id)
            args.putString("thumbnail", data.thumbnail)
            args.putString("history", keyword)

            val detailFragment: Fragment = DetailFragment()
            detailFragment.arguments = args

            val activity = view.context as AppCompatActivity
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
        return resultRecipes.size
    }

    fun updateResultRecipes(data: ArrayList<RecipeDTO.RecipeFinal>) {
        resultRecipes.clear()
        resultRecipes.addAll(data)
    }
}