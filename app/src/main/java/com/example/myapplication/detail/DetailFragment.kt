package com.example.myapplication.detail

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.google.android.material.tabs.TabLayout

class DetailFragment : Fragment() {

    private lateinit var v: View

    internal lateinit var viewPagerPics: ViewPager
    internal lateinit var viewPagerSteps: ViewPager2
    internal lateinit var tabLayout: TabLayout
    internal lateinit var rvReviews: RecyclerView
    internal lateinit var ibRating:ImageButton

    private lateinit var commentAdapter: DetailCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_detail, container, false)

        setViewPagerPics()
        setStarRatingBtn()

        viewPagerSteps = v.findViewById<ViewPager2>(R.id.vp_comments)

        rvReviews = v.findViewById<RecyclerView>(R.id.rv_comment)



        val adapterStepDescription = DetailViewPagerStepsAdapter(v.context)
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

        commentAdapter = DetailCommentAdapter()
        commentAdapter.addComment(RecipeDTO.Comment("1","R.drawable.ic_home","닉네임1","2020.02.12","존맛"))
        commentAdapter.addComment(RecipeDTO.Comment("2","R.drawable.ic_home","닉네임1","2020.02.12","존맛"))
        commentAdapter.addComment(RecipeDTO.Comment("3","R.drawable.ic_home","닉네임1","2020.02.12","존맛"))
        commentAdapter.addComment(RecipeDTO.Comment("4","R.drawable.ic_home","닉네임1","2020.02.12","존맛"))

        val rvComment = v.findViewById<RecyclerView>(R.id.rv_comment)
        rvComment.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        rvComment.setHasFixedSize(true)
        rvComment.adapter = commentAdapter

        return v
    }

    private fun setStarRatingBtn() {
        ibRating = v.findViewById<ImageButton>(R.id.iv_like)
    }

    private fun setViewPagerPics() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater
                .from(context).inflateTransition(
                    android.R.transition.move // you can change this
                )
        }

        viewPagerPics = v.findViewById<ViewPager>(R.id.vp_recipes)
        ViewCompat.setTransitionName(viewPagerPics,"@string/transition_random_to_detail")

        tabLayout = v.findViewById<TabLayout>(R.id.tab_layout)

        val adapterPic = DetailViewPagerPicsAdapter(v.context)
        adapterPic.addRecipeImage(R.drawable.ic_home)
        adapterPic.addRecipeImage(R.drawable.ic_no_image)
        adapterPic.addRecipeImage(R.drawable.ic_face)
        viewPagerPics.adapter = adapterPic
        tabLayout.setupWithViewPager(viewPagerPics)//Circle Indicator 추가
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)

        viewPagerPics.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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