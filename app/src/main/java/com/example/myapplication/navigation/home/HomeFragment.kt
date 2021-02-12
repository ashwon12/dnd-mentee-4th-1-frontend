package com.example.myapplication.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_home.*
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment() {

    private lateinit var v : View
    private var top3ImagesList = mutableListOf<Int>()

    private lateinit var vp_top3 : ViewPager2
    private lateinit var indicator : CircleIndicator3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        postToList()

        vp_top3 = v.findViewById(R.id.vp_top3)
        vp_top3.adapter = ViewPagerAdapter(top3ImagesList)
        vp_top3.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicator = v.findViewById(R.id.indicator)
        indicator.setViewPager(vp_top3)

        return v
    }

    private fun addToList(image : Int){
        top3ImagesList.add(image)
    }

    private fun postToList(){
        addToList(R.drawable.dumi1)
        addToList(R.drawable.dumi2)
        addToList(R.drawable.dumi3)
    }
}