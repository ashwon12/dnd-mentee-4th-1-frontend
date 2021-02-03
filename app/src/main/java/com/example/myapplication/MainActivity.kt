package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.navigation.home.HomeFragment
import com.example.myapplication.navigation.search.SearchFragment
import com.example.myapplication.navigation.timeline.TimelineFragment
import com.example.myapplication.navigation.upload.UploadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val searchFragment by lazy { SearchFragment()}
    private val listFragment by lazy { TimelineFragment() }
    private val writeFragment by lazy { UploadFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()
    }

    /**
     *  <p> 하단 네비게이션 이벤트 처리 함수 </p>
     *
     */
    private fun initBottomNavigation() {
        bnv_home.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        setFragment(homeFragment)
                    }
                    R.id.search -> {
                        setFragment(searchFragment)
                    }
                    R.id.list -> {
                        setFragment(listFragment)
                    }
                    R.id.write -> {
                        setFragment(writeFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.home // 초기 프래그먼트
        }
    }

    /**
     *  <p> 프래그먼트 전환 함수 </p>
     *
     * @param fragment: Fragment 전환될 프래그먼트
     */
    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}