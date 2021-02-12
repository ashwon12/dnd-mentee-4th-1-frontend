package com.example.myapplication.navigation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ViewPagerAdapter(private var top3Images : List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.HolderViewPager>() {

    inner class HolderViewPager(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val top3ImagesItem : ImageView = itemView.findViewById(R.id.iv_top3_image_item)

        init {
            top3ImagesItem.setOnClickListener { v :View ->
                val position = adapterPosition
                Toast.makeText(itemView.context, "너는 ${position + 1}번 클릭 했다.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.HolderViewPager {
        return HolderViewPager(LayoutInflater.from(parent.context).inflate(R.layout.home_vp_item,parent,false))
    }



    override fun getItemCount(): Int {
        return top3Images.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.HolderViewPager, position: Int) {
        holder.top3ImagesItem.setImageResource(top3Images[position])
    }
}