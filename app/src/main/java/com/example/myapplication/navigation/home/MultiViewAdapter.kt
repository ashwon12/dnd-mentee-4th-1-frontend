package com.example.myapplication.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.detail.DetailFragment

class MultiViewAdapter(

    private var type: Int,
    private var ItemsList: ArrayList<RecipeDTO.RecipeFinal>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var view : View

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_vp_item, parent, false)
                ViewPagerViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_gv_item, parent, false)
                GridViewHolder(view)
            }
            3 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_rv_item, parent, false)
                ListViewHolder(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemCount(): Int {
        return ItemsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                Glide.with(App.instance)
                    .load(ItemsList[position].thumbnail)
                    .placeholder(R.drawable.ic_no_image)
                    .into((holder as ViewPagerViewHolder).top3Image)
            }

            2 -> {
                for (i in 0..4) {
                    Glide.with(App.instance)
                        .load(ItemsList[i].thumbnail)
                        .placeholder(R.drawable.ic_no_image)
                        .into((holder as GridViewHolder).popularImage)
                }
            }

            3 -> {
                val mainIngredients = ItemsList[position].mainIngredients
                val sb = StringBuilder()
                for (text in mainIngredients!!) {
                    sb.append(text.name + "\t")
                }
                sb.toString()
                (holder as ListViewHolder).recentContent.text = sb
                holder.recentTitle.text = ItemsList[position].title
                Glide.with(App.instance)
                    .load(ItemsList[position].thumbnail)
                    .placeholder(R.drawable.ic_no_image)
                    .into(holder.recentImage)
            }
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(
                App.instance,
                "ID : ${this.ItemsList[position].id} " +
                        "Title : ${this.ItemsList[position].title}",
                Toast.LENGTH_SHORT
            ).show()

            val activity = view.context as AppCompatActivity
            val detailFragment: Fragment = DetailFragment()

            val args = Bundle()// 클릭된 Recipe의 id 전달
            args.putInt("recipeId",ItemsList[position].id)

            detailFragment.arguments = args

            val manager: FragmentManager = activity.supportFragmentManager
            manager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .setReorderingAllowed(true)
                .replace(R.id.fl_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val top3Image: ImageView = itemView.findViewById(R.id.iv_top3_image_item)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularImage: ImageView = itemView.findViewById(R.id.iv_popular_image_item)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recentImage: ImageView = itemView.findViewById(R.id.iv_recent_item)
        val recentTitle: TextView = itemView.findViewById(R.id.tv_home_title)
        val recentContent: TextView = itemView.findViewById(R.id.tv_home_content)
    }
}


