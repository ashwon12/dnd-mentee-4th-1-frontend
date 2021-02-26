package com.example.myapplication.navigation.feed

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.SharedPreferenceUtil
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.navigation.home.ThemesAdapter
import me.relex.circleindicator.CircleIndicator3

class FeedRecyclerViewHolder(
    v: View,
    recyclerInterface: FeedRecyclerInterface
) : RecyclerView.ViewHolder(v),
    View.OnClickListener {

    private val profile = v.findViewById<ImageView>(R.id.iv_feed_profile)
    private val userName = v.findViewById<TextView>(R.id.tv_feed_username)
    private val moreSet = v.findViewById<LinearLayout>(R.id.ll_feed_more_set)
    private val btn_more = v.findViewById<ImageView>(R.id.ib_feed_more)
    private val btn_delete = v.findViewById<Button>(R.id.btn_feed_delete)
    private val btn_modify = v.findViewById<Button>(R.id.btn_feed_modify)
    private val title = v.findViewById<TextView>(R.id.tv_feed_title)
    private val contentsText = v.findViewById<TextView>(R.id.tv_feed_contents)
    private val starCount = v.findViewById<TextView>(R.id.tv_feed_star_count)
    private val viewCount = v.findViewById<TextView>(R.id.tv_feed_views_count)
    private val writeDate = v.findViewById<TextView>(R.id.tv_feed_date)
    private val rv_feed_theme = itemView.findViewById<RecyclerView>(R.id.rv_feed_theme)

    private val vpItem = v.findViewById<ViewPager2>(R.id.vp_feed)
    private val feedIndicator = v.findViewById<CircleIndicator3>(R.id.indicator_feed)

    private var myInterface: FeedRecyclerInterface? = null

    //기본 생성자
    init {
        v.setOnClickListener(this)
        this.myInterface = recyclerInterface
    }

    fun bind(data: RecipeDTO.RecipeFinal) {
        title.text = data.title
        starCount.text = data.starCount.toString()
        userName.text = data.writer?.name
        contentsText.text = data.description
        viewCount.text = data.viewCount
        val writeDates = data.createdDate.toString()
       // writeDate.text = writeDates.substring(0,10)

        Glide.with(App.instance)
            .load(data.writer?.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .circleCrop()
            .into(profile)


        btn_more.setOnClickListener {
            when (moreSet.visibility) {
                View.VISIBLE -> moreSet.visibility = View.INVISIBLE
                View.INVISIBLE -> moreSet.visibility = View.VISIBLE
            }
        }

        btn_delete.setOnClickListener {
            Toast.makeText(
                App.instance,
                "id ${data.id}번의 삭제 버튼 클릭 ",
                Toast.LENGTH_SHORT
            ).show()
        }

        btn_modify.setOnClickListener {
            Toast.makeText(
                App.instance,
                "id ${data.id}번의 수정 버튼 클릭 ",
                Toast.LENGTH_SHORT
            ).show()
        }

        setViewPager(data)
        setThemes(data)
    }

    private fun setThemes(data: RecipeDTO.RecipeFinal) {
        val themeList = data.themes
        val themesAdapter = ThemesAdapter(themeList)
        rv_feed_theme.layoutManager = LinearLayoutManager(App.instance, RecyclerView.HORIZONTAL, false)
        rv_feed_theme.adapter = themesAdapter
    }

    private fun setViewPager(data: RecipeDTO.RecipeFinal) {
        val stepList = data.steps
        vpItem.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpItem.adapter = FeedViewPagerAdapter(stepList)
        feedIndicator.setViewPager(vpItem)
    }

    override fun onClick(p0: View?) {
        this.myInterface?.onItemClicked(adapterPosition)
    }
}

