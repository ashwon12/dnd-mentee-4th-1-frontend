package com.example.myapplication.navigation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.detail.DetailFragment

class FollowAdapter(
    private var userList: ArrayList<RecipeDTO.User>
) :
    RecyclerView.Adapter<FollowAdapter.UserViewHolder>() {

    lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.mypage_follow_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: FollowAdapter.UserViewHolder, position: Int) {
        Glide.with(App.instance)
            .load(userList.get(position).imgUrl)
            .placeholder(R.drawable.ic_no_image)
            .circleCrop()
            .into(holder.followProfile)

        holder.followName.text = userList[position].name
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val followProfile = itemView.findViewById<ImageView>(R.id.iv_follow_profile)
        val followName = itemView.findViewById<TextView>(R.id.tv_follow_name)
    }
}