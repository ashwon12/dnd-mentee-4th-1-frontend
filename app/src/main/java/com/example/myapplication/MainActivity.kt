package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.navigation.feed.FeedFragment
import com.example.myapplication.navigation.home.HomeFragment
import com.example.myapplication.navigation.mypage.MyPageFragment
import com.example.myapplication.navigation.search.SearchFragment
import com.example.myapplication.navigation.upload.UploadActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getItems()

        if(flag == 1 && cancel.equals("0")) {
            showDialog()
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setStatusBarTransparent()
        initBottomNavigation()
    }

    fun getItems(){
        if(intent.hasExtra("join")) {
            flag = intent.getIntExtra("join", -1)
            Log.d("flag", flag.toString())
        }
        if(intent.hasExtra("cancel")) {
            cancel = intent.getStringExtra("cancel")!!
        }
    }

    fun showDialog() {
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
                Toast.makeText(App.instance, "누름", Toast.LENGTH_SHORT).show()
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
                    Log.d("join fail!!!!!!!!!", "fail")
                })
        }
    }
    fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
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