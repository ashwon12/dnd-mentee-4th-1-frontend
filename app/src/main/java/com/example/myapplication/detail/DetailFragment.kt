package com.example.myapplication.detail

import android.R.attr.rating
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
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
    internal lateinit var rvComment: RecyclerView
    internal lateinit var ibRating:ImageButton
    internal lateinit var llStarRating:LinearLayout

    private lateinit var commentAdapter: DetailCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_detail, container, false)

        setViewPagerPics()
        setStarRatingBtn()
        setCommentRecyclerView()
        setViewPagerSteps()

/*        val tvRatingResult = v.findViewById<TextView>(R.id.tv_star_rating)
        llStarRating = v.findViewById(R.id.ll_star_rating)
        val ratClick =
            OnRatingBarChangeListener { ratingBar, r, b ->
                val num = r

                // 별 갯수에 따른 한칸의 점수
                val st: Float = 10.0f / rating.getNumStars()

                // 소수점 한자리로 결과값 끊어주기
                val str = String.format("%.1f", st * r)
                tvRatingResult.text = "$str/10.0"
            }
        llStarRating.setOnClickListener {
            val dialog = Dialog(v.context);
            dialog.setCancelable(false);
            // 다이얼로그 화면 설정
            dialog.setContentView(R.layout.dialogue_star_rating);

            val rating : RatingBar = dialog.findViewById(R.id.ratingbar);
            val btn_ok : Button = dialog.findViewById(R.id.btn_ok);

            rating.setRating(3f) // 레이팅 바에 기본값 채우기
            rating.setIsIndicator(false)// 사용자가 임의로 별점을 바꿀수 있도록 허가하는 메서드
            rating.setStepSize(1f)// 한칸당 1 점으로 할당

            // 레이팅바의 변경사항을 감자히는 감지자
            rating.onRatingBarChangeListener = ratClick;

            // 다이얼로그 보이기
            dialog.show();

            btn_ok.setOnClickListener(click);
        }*/


        return v
    }

    /**  ViewPager : 요리순서  **/
    private fun setViewPagerSteps() {
        viewPagerSteps = v.findViewById<ViewPager2>(R.id.vp_comments)

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
    }

    /**  RecyclerView : 댓글  **/
    private fun setCommentRecyclerView() {
        rvComment = v.findViewById<RecyclerView>(R.id.rv_comment)
        commentAdapter = DetailCommentAdapter()
        commentAdapter.addComment(
            RecipeDTO.Comment(
                "1",
                "R.drawable.ic_home",
                "닉네임1",
                "2020.02.12",
                "존맛개노맛존맛개노맛존맛개노맛존맛개노맛존맛개노맛존맛개노맛존맛개노맛존맛개노맛존맛개노맛존맛개노맛"
            )
        )
        commentAdapter.addComment(
            RecipeDTO.Comment(
                "2",
                "R.drawable.ic_home",
                "닉네임1",
                "2020.02.12",
                "존맛"
            )
        )
        commentAdapter.addComment(
            RecipeDTO.Comment(
                "3",
                "R.drawable.ic_home",
                "닉네임1",
                "2020.02.12",
                "존맛"
            )
        )
        commentAdapter.addComment(
            RecipeDTO.Comment(
                "4",
                "R.drawable.ic_home",
                "닉네임1",
                "2020.02.12",
                "존맛"
            )
        )

        rvComment.layoutManager = LinearLayoutManager(
            v.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvComment.setHasFixedSize(true)
        rvComment.adapter = commentAdapter
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
        ViewCompat.setTransitionName(viewPagerPics, "@string/transition_random_to_detail")

        tabLayout = v.findViewById<TabLayout>(R.id.tab_layout)

        val adapterPic = DetailViewPagerPicsAdapter(v.context)
        adapterPic.addRecipeImage(R.drawable.ic_home)
        adapterPic.addRecipeImage(R.drawable.ic_no_image)
        adapterPic.addRecipeImage(R.drawable.ic_face)
        viewPagerPics.adapter = adapterPic
        tabLayout.setupWithViewPager(viewPagerPics)//Circle Indicator 추가
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.shared_image)
    }
}