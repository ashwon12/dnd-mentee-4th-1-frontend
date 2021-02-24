package com.example.myapplication.navigation.mypage

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

class MyMultiViewAdapter(
    private var type: Int,
    private var ItemsList: ArrayList<RecipeDTO.RecipeFinal>
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var view : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.mypage_recipie_item, parent, false)
                GridViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.mypage_follow_item, parent, false)
                UserViewHolder(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemCount(): Int {
        return ItemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                Glide.with(App.instance)
                    .load(ItemsList[position].thumbnail)
                    .placeholder(R.drawable.ic_no_image)
                    .into((holder as MyMultiViewAdapter.GridViewHolder).myThumbnail)

                holder.myTitle.text = ItemsList[position].title
                holder.myStarCount.text = ItemsList[position].starCount
                holder.myViewCount.text = ItemsList[position].viewCount
                holder.myTime.text = ItemsList[position].time
            }

            2 -> {

            }
        }
    }

    inner class GridViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val myThumbnail = itemView.findViewById<ImageView>(R.id.iv_my_thumbnail)
        val myTitle = itemView.findViewById<TextView>(R.id.tv_my_recipie_title)
        val myStarCount = itemView.findViewById<TextView>(R.id.tv_my_star_count)
        val myViewCount = itemView.findViewById<TextView>(R.id.tv_my_views_count)
        val myTime = itemView.findViewById<TextView>(R.id.tv_my_time)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}