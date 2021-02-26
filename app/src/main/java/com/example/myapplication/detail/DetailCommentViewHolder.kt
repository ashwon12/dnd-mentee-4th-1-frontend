package com.example.myapplication.detail

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class DetailCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivProfilePic = itemView.findViewById<ImageView>(R.id.iv_profile_pic)
    private val tvNickname = itemView.findViewById<TextView>(R.id.tv_nickname)
    private val tvDate = itemView.findViewById<TextView>(R.id.tv_upload_date)
    private val tvComment = itemView.findViewById<TextView>(R.id.tv_comment)

    fun bindItem(data: RecipeDTO.Comment) {

        ivProfilePic.clipToOutline = true// 확인 필요
        data.imageUrl?.let {
            if (it.isNotEmpty()) {
                Glide.with(App.instance)
                    .load(data.imageUrl)
                    .placeholder(R.drawable.ic_face)
                    .into(ivProfilePic)
            }
        }
        tvNickname.text = data.writer?.name
        tvDate.text = data.createdDate
        tvComment.text = data.content
    }
}