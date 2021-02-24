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

class MyMultiViewAdapter(
    private var type: Int,
    private var ItemsList: ArrayList<RecipeDTO.RecipeFinal>
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var view : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
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
                holder.myTime.text = "${ItemsList[position].time}분"

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

            2 -> {
                Glide.with(App.instance)
                    .load(ItemsList[position].thumbnail)
                    .placeholder(R.drawable.ic_no_image)
                    .circleCrop()
                    .into((holder as MyMultiViewAdapter.UserViewHolder).followProfile)

                holder.followName.text = ItemsList[position].writer!!.name
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
        val followProfile = itemView.findViewById<ImageView>(R.id.iv_follow_profile)
        val followName = itemView.findViewById<TextView>(R.id.tv_follow_name)
    }
}