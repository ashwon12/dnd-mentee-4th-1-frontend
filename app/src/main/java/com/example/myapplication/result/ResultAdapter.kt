package com.example.myapplication.result

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

        holder.itemView.setOnClickListener {

            val itemImageView = view.findViewById<ImageView>(R.id.iv_result_image)
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
        return resultRecipes.size
    }

    fun addSampleResult(data: RecipeDTO.tempResultRecipes) {
        resultRecipes.add(data)
    }
}