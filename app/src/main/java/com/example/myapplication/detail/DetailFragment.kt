package com.example.myapplication.detail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.google.android.material.tabs.TabLayout


class DetailFragment : Fragment() {

    private lateinit var v: View

    internal lateinit var viewPagerPics: ViewPager
    internal lateinit var rvSteps: RecyclerView
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
        setStarRatingButton()
        setCommentRecyclerView()
        setViewPagerSteps()
        setRatingBar()

        return v
    }




    private fun setRatingBar() {
        val ratingBar = v.findViewById(R.id.ratingbar) as RatingBar
        val stars = ratingBar.progressDrawable as LayerDrawable
        stars.getDrawable(2).setTint(Color.rgb(255,217,81))
    }

    /**  RecyclerView : 요리순서  **/
    private fun setViewPagerSteps() {
        rvSteps = v.findViewById<RecyclerView>(R.id.rv_steps)

        val adapterStepDescription = DetailStepsAdapter()
        adapterStepDescription.addDescription("물을 끓여주세요.")
        adapterStepDescription.addDescription("라면 봉지를 뜯어주세요. 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 글자 줄바꾸기 테스트용 ")
        adapterStepDescription.addDescription("가루스프를 넣어주세요")
        adapterStepDescription.addDescription("라면사리를 넣어주세요.")

        rvSteps.layoutManager = LinearLayoutManager(v.context,LinearLayoutManager.VERTICAL,false)
        rvSteps.setHasFixedSize(true)
        rvSteps.adapter = adapterStepDescription

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

    private fun setStarRatingButton() {
        ibRating = v.findViewById<ImageButton>(R.id.iv_like)
        ibRating.setOnClickListener {//평점주기 버튼 클릭 리스너

            val dialog = Dialog(v.context);
            dialog.setCancelable(false);
            // 다이얼로그 화면 설정
            dialog.setContentView(R.layout.dialogue_star_rating)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            val rating : RatingBar = dialog.findViewById(R.id.ratingbar);
            val btn_ok : Button = dialog.findViewById(R.id.btn_ok);
            val btn_cancel : Button = dialog.findViewById(R.id.btn_cancel)

            rating.setRating(3f) // 레이팅 바에 기본값 채우기
            rating.setIsIndicator(false)// 사용자가 임의로 별점을 바꿀수 있도록 허가하는 메서드
            rating.setStepSize(1f)// 한칸당 1 점으로 할당

            val starsInRatingDialogue = rating.progressDrawable as LayerDrawable
            starsInRatingDialogue.getDrawable(2).setTint(Color.rgb(255,217,81))

            btn_ok.setOnClickListener {
                dialog.dismiss()
                ibRating.setBackgroundResource(R.drawable.ic_home);
                //TODO : 서버 요청
            }
            btn_cancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show();
        }
    }

    private fun setViewPagerPics() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater
                .from(context)
                .inflateTransition(R.transition.shared_image)// 바꿀 수 있음
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