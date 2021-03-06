package com.example.yorijori.navigation.mypage

import MyPageFollowerFragment
import MyPageFollowingFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagePagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val mypageList = ArrayList<Fragment>()
    private val mypageTitleList = ArrayList<String>()

    override fun getCount(): Int {
        return mypageList.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MyPageRecipeFragment()
            1 -> MyPageFollowerFragment()
            2 -> MyPageFollowingFragment()
            else -> MyPageRecipeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mypageTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mypageList.add(fragment)
        mypageTitleList.add(title)
    }
}