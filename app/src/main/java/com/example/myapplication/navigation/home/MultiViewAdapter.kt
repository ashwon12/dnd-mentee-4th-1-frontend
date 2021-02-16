package com.example.myapplication.navigation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R


class MultiViewAdapter(
    private var type: Int,
    private var Images: ArrayList<String>
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
                    .inflate(R.layout.home_vp_item, parent, false)
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
                Glide.with(App.instance)
                    .load(Images[position])
                    .placeholder(R.drawable.ic_no_image)
                    .into((holder as ViewPagerViewHolder).top3Image)
            }

            2 -> {
                Glide.with(App.instance)
                    .load(Images[position])
                    .placeholder(R.drawable.ic_no_image)
                    .into((holder as GridViewHolder).popularImage)
            }

            3 -> {
                Log.d("view Type 3","haha")
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

    }
}


