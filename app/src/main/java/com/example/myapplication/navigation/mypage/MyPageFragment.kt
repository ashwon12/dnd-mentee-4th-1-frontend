package com.example.myapplication.navigation.mypage

import MyPageFollowerFragment
import MyPageFollowingFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_mypage.*

class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()

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