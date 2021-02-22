package com.example.myapplication.navigation.home

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class MultiViewAdapter(
    private var type: Int,
    private var Images: ArrayList<RecipeDTO.RecipeFinal>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View?
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
        return Images.size
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                for(i in 0 .. 2) {
                    Glide.with(App.instance)
                        .load(Images[i].thumbnail)
                        .placeholder(R.drawable.ic_no_image)
                        .into((holder as ViewPagerViewHolder).top3Image)
                }
            }

            2 -> {
                for(i in 0 .. 4){
                    Glide.with(App.instance)
                        .load(Images[i].thumbnail)
                        .placeholder(R.drawable.ic_no_image)
                        .into((holder as GridViewHolder).popularImage)
                }
            }

            3 -> {
                val mainIngredients = Images[position].mainIngredients
                val sb = StringBuilder()
                for (text in mainIngredients){
                    sb.append(text.name+"\t")
                }
                sb.toString()
                (holder as ListViewHolder).recentContent.text = sb
                holder.recentTitle.text = Images[position].title
                Glide.with(App.instance)
                    .load(Images[position].thumbnail)
                    .placeholder(R.drawable.ic_no_image)
                    .into(holder.recentImage)
            }
        }
    }

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val top3Image: ImageView = itemView.findViewById(R.id.iv_top3_image_item)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularImage: ImageView = itemView.findViewById(R.id.iv_popular_image_item)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recentImage : ImageView = itemView.findViewById(R.id.iv_recent_item)
        val recentTitle : TextView = itemView.findViewById(R.id.tv_home_title)
        val recentContent : TextView = itemView.findViewById(R.id.tv_home_content)
    }
}


