package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.Fragment.HomeFragment
import com.example.myapplication.Fragment.ListFragment
import com.example.myapplication.Fragment.WriteFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val listFragment by lazy { ListFragment() }
    private val writeFragment by lazy { WriteFragment() }

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
                when(it.itemId){
                    R.id.home ->{
                        setFragment(homeFragment)
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
    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}