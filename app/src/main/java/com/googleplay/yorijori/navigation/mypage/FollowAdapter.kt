package com.example.yorijori.navigation.mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yorijori.App
import com.example.yorijori.R
import com.example.yorijori.SharedPreferenceUtil
import com.example.yorijori.data.datasource.remote.api.RecipeDTO
import com.example.yorijori.data.repository.Repository

class FollowAdapter(
    private var userList: ArrayList<RecipeDTO.User>
) :
    RecyclerView.Adapter<FollowAdapter.UserViewHolder>() {

    lateinit var view: View
    private val repository = Repository()

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
        val userID = userList.get(position).id
        val userProfile = userList.get(position).imgUrl

        Glide.with(App.instance)
            .load(userProfile)
            .placeholder(R.drawable.ic_no_image)
            .circleCrop()
            .into(holder.followProfile)


        holder.followName.text = userList[position].name
        holder.followButton.setOnClickListener {
            repository.userUnFollow(
                success = {
                    it.run {
                        Log.d("MyPage Fragment", "${userID}번 유저 언팔로우 하셨습니다!")
                        holder.followButton.setText("unFollow")
                    }
                },
                fail = {
                    Log.d("fail", "fail fail fail")
                },
                token = SharedPreferenceUtil(App.instance).getToken().toString(),
                followingId =userID
            )

        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val followProfile = itemView.findViewById<ImageView>(R.id.iv_follow_profile)
        val followName = itemView.findViewById<TextView>(R.id.tv_follow_name)
        val followButton = itemView.findViewById<Button>(R.id.btn_my_unfollow)
    }
}