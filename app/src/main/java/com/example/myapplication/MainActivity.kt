package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.navigation.timeline.TimelineFragment
import com.example.myapplication.navigation.home.HomeFragment
import com.example.myapplication.navigation.upload.UploadActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val feedFragment by lazy { TimelineFragment() }

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
        bnv_home.background=null
        bnv_home.menu.getItem(2).isEnabled = false
        bnv_home.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home ->{
                        setFragment(homeFragment)
                    }
                    R.id.feed -> {
                        setFragment(feedFragment)
                    }
                    R.id.search->{

                    }
                    R.id.myPage->{

                    }
                }
                true
            }
            selectedItemId = R.id.home // 초기 프래그먼트
        }

        fab_write.setOnClickListener {
            val intent = Intent(App.instance, UploadActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     *  <p> 프래그먼트 전환 함수 </p>
     *
     * @param fragment: Fragment 전환될 프래그먼트
     */
    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}