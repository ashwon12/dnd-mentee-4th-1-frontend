package com.example.yorijori.navigation.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yorijori.App
import com.example.yorijori.R
import com.example.yorijori.data.datasource.remote.api.RecipeDTO

class FeedViewPagerAdapter(private val Images : ArrayList<RecipeDTO.Steps>) :
    RecyclerView.Adapter<FeedViewPagerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewPagerAdapter.Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.home_vp_item, parent, false))
    }

    override fun getItemCount(): Int {
        return Images.size
    }

    override fun onBindViewHolder(holder: FeedViewPagerAdapter.Holder, position: Int) {
        Glide.with(App.instance)
            .load(Images[position].imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .into(holder.thumbnail)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val thumbnail = itemView!!.findViewById<ImageView>(R.id.iv_top3_image_item)
    }

}