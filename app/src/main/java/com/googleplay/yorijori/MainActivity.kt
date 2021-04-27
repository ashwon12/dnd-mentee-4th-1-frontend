package com.googleplay.yorijori

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.googleplay.yorijori.base.BaseActivity
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.data.repository.Repository
import com.googleplay.yorijori.navigation.feed.FeedFragment
import com.googleplay.yorijori.navigation.home.HomeFragment
import com.googleplay.yorijori.navigation.mypage.MyPageFragment
import com.googleplay.yorijori.navigation.search.SearchFragment
import com.googleplay.yorijori.navigation.upload.UploadActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(R.layout.activity_main) {

    private val homeFragment by lazy { HomeFragment() }
    private val feedFragment by lazy { FeedFragment() }
    private val searchFragment by lazy { SearchFragment() }
    private val myPageFragment by lazy { MyPageFragment() }

    private var joinInfo = RecipeDTO.RequestJoin("", "", "")
    private var flag = 1
    private var cancel = "0"
    private val repository = Repository()
    private var token = App.sharedPrefs.getToken()
    private var name = ""
    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLoginInfo()

        Log.d("cancel", cancel + "here")
        if(flag == 1 && cancel.equals("0") || isFirst) {
            isFirst = false
            showDialog()
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        initBottomNavigation()
    }

    fun getLoginInfo(){
        if(intent.hasExtra("join")) {
            flag = intent.getIntExtra("join", -1)
            Log.d("flag", flag.toString())
        }
        if(intent.hasExtra("cancel")) {
            cancel = intent.getStringExtra("cancel")!!
            Log.d("cancel", cancel + "fsdfsdfsd")
        }
    }

    fun showDialog() {
        if( cancel.equals("10001")) {
            Toast.makeText(this, "레시피가 등록되었습니다!", Toast.LENGTH_SHORT).show()
        } else {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            val edialog = LayoutInflater.from(this)
            val mView = edialog.inflate(R.layout.dialog_nickname, null)
            val dialogText1 = mView.findViewById<TextView>(R.id.tv_nickname_title)
            val dialogText2 = mView.findViewById<TextView>(R.id.tv_nickname_subtitle)
            val nickname = mView.findViewById<EditText>(R.id.et_user_nickname)
            val dialogText3 = mView.findViewById<TextView>(R.id.tv_nickname_warning)
            val submitButton = mView.findViewById<TextView>(R.id.btn_user_nickname_submit)

            dialog.setContentView(mView)
            submitButton.setOnClickListener {
                if(name.length >= 1) {
                    App.sharedPrefs.saveName(name)
                    postJoinInfo()
                    dialog.cancel()
                    //Toast.makeText(App.instance, "누름", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(App.instance, "한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            nickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    name = s.toString()
                }
            })

            dialog.create()
            dialog.show()
        }
    }

    fun postJoinInfo(){
        if (App.sharedPrefs.getToken() != null && App.sharedPrefs.getEmail() != null) {
            joinInfo.email = App.sharedPrefs.getEmail()
            joinInfo.imageUrl = App.sharedPrefs.getImage()
            joinInfo.name = App.sharedPrefs.getName()

            repository.postJoinInfo(token!!, joinInfo,
                success = {
                    it.run {
                        val data= it.data
                        if(App.sharedPrefs.getFlag() == "1") {
                            App.sharedPrefs.saveKakaoId(data!!)
                            Log.d("join id", App.sharedPrefs.getKakaoId().toString())
                        } else {
                            App.sharedPrefs.saveGoogleId(data!!)
                        }
                    }

                    Log.d("success", "success")
                }, fail = {
                    Log.e("MainActivty", "postJoinInfo")
                })
        }
    }

    /**
     *  <p> 하단 네비게이션 이벤트 처리 함수 </p>
     *
     */
    private fun initBottomNavigation() {
        bnv_home.background = null
        bnv_home.menu.getItem(2).isEnabled = false
        bnv_home.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        setFragment(homeFragment)
                    }
                    R.id.feed -> {
                        setFragment(feedFragment)
                    }
                    R.id.search -> {
                        setFragment(searchFragment)
                    }
                    R.id.myPage -> {
                        setFragment(myPageFragment)
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
    private fun setFragment(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
            .replace(R.id.fl_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}