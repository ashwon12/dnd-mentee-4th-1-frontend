package com.example.myapplication.navigation.mypage

import MyPageFollowerFragment
import MyPageFollowingFragment
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.fragment_mypage.*


class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateKaKaoLoginUi()
        updateGoogleLoginUi()
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

    private fun updateKaKaoLoginUi() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    "kakao", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.profileImageUrl}"
                )
                tv_user_id.setText(user.kakaoAccount?.profile?.nickname)
                Glide.with(App.instance)
                    .load(user.kakaoAccount?.profile?.profileImageUrl)
                    .into(iv_user_profile)
            }
        }
    }

    private fun updateGoogleLoginUi() {
        val acct = GoogleSignIn.getLastSignedInAccount(App.instance)

        if (acct != null) {
            val personName = acct?.displayName
            val personGivenName = acct?.givenName
            val personFamilyName = acct?.familyName
            val personEmail = acct?.email
            val personId = acct?.id
            val personPhoto: Uri? = acct?.photoUrl

            Log.d(
                "google",
                personName + "," + personGivenName + "," + personFamilyName + "," + personEmail + "," + personId + "," + acct.idToken
            )
            tv_user_id.setText(personGivenName)
            Glide.with(App.instance)
                .load(personPhoto)
                .into(iv_user_profile)
        } else {
            Log.d("google", "access fail")
        }
    }
}