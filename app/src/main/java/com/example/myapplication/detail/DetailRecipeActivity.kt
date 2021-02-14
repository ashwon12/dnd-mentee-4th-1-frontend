package com.example.myapplication.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailRecipeActivity : AppCompatActivity() {

    internal lateinit var viewPagerPic: ViewPager
    internal lateinit var viewPagerSteps: ViewPager2
    internal lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recipe)

        viewPagerPic = findViewById<ViewPager>(R.id.vp_recipes)
        viewPagerSteps = findViewById<ViewPager2>(R.id.vp_comments)
        tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val adapterPic = DetailViewPagerPicsAdapter(this)
        adapterPic.addRecipeImage(R.drawable.ic_home)
        adapterPic.addRecipeImage(R.drawable.ic_no_image)
        adapterPic.addRecipeImage(R.drawable.ic_face)
        viewPagerPic.adapter = adapterPic
        tabLayout.setupWithViewPager(viewPagerPic)//Circle Indicator 추가

        val adapterStepDescription = DetailViewPagerStepsAdapter(this)
        adapterStepDescription.addDescription("물을 끓여주세요.")
        adapterStepDescription.addDescription("라면 봉지를 뜯어주세요. 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 ")
        adapterStepDescription.addDescription("가루스프를 넣어주세요")
        adapterStepDescription.addDescription("라면사리를 넣어주세요.")
        viewPagerSteps.adapter = adapterStepDescription



        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pagerHeight)
        val viewPagerHeight = 150
        val offsetPx = viewPagerHeight - pageMarginPx - pagerWidth

        viewPagerSteps.setPageTransformer { page, position ->
            page.translationY = position * -offsetPx
        }

        viewPagerPic.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(// 페이지가 스크롤 되었을 때
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewPagerSteps.setPageTransformer { page, position ->

                }
                return
            }

            override fun onPageSelected(position: Int) {// 페이지를 클릭 했을
                viewPagerSteps.verticalScrollbarPosition = position
                return
            }

            override fun onPageScrollStateChanged(state: Int) {// 스크롤 상태가 변경되었을 때
                viewPagerSteps.verticalScrollbarPosition = state
                return
            }

        })

    }
}