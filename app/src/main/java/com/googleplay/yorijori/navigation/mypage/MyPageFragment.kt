package com.example.yorijori.navigation.mypage

import MyPageFollowerFragment
import MyPageFollowingFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.yorijori.App
import com.example.yorijori.R
import kotlinx.android.synthetic.main.fragment_mypage.*
import com.example.yorijori.SharedPreferenceUtil as SharedPreferenceUtil1


class MyPageFragment : Fragment() {

    private lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabs()
        settingButton()
        setUser()
    }

    private fun setUser() {
        val profile = v.findViewById<ImageView>(R.id.iv_user_profile)
        val userName = v.findViewById<TextView>(R.id.tv_user_id)
        val imageUrl = SharedPreferenceUtil1(App.instance).getImage()

        Glide.with(App.instance)
            .load(imageUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_no_image)
            .into(profile)

        userName.text = SharedPreferenceUtil1(App.instance).getName()
    }

    private fun settingButton() {
        ib_settings.setOnClickListener {
            activity?.let{
                val intent = Intent(context, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initTabs() {
        val adapter by lazy { MyPagePagerAdapter(childFragmentManager) }

        adapter.addFragment(MyPageRecipeFragment(), "레시피")
        adapter.addFragment(MyPageFollowerFragment(), "팔로워")
        adapter.addFragment(MyPageFollowingFragment(), "팔로잉")
        mypage_viewpager.adapter = adapter

        mypage_tablayout.setupWithViewPager(mypage_viewpager)
    }

}