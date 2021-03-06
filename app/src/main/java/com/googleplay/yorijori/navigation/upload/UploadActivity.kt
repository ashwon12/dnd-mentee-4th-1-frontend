package com.example.yorijori.navigation.upload

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yorijori.MainActivity
import com.example.yorijori.R
import com.example.yorijori.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    private var recipeTitle: String = ""
    private var filterList = ArrayList<RecipeDTO.Themes>()
    private var themes = ArrayList<RecipeDTO.Themes>()
    private var saveFilterList = ArrayList<String>()
    private var subTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        setStatusBarTransparent()

        textWatcher()

        btn_upload_recipe_next1.setOnClickListener { clickNextButton() }
        iv_upload_cancel.setOnClickListener { clickCancelButton() }
        filterAdd()

        callAdapter()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun clickNextButton() {
        if (checkPermissionNextButton()) {
            val intent = Intent(this, UploadActivity2::class.java)
            intent.putExtra("recipeTitle", recipeTitle)
            intent.putExtra("filter", saveFilterList)
            intent.putExtra("originFilter", filterList)
            intent.putExtra("subtitle", subTitle)
            intent.putExtra("themes", themes)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        } else {

        }
    }

    private fun clickCancelButton() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(
            "나중에 올릴 땐 다시 작성해야해요\n" +
                    "작성을 멈추시겠어요?"
        )
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("cancel", "1")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun callAdapter() {
        rv_upload_filter.layoutManager =
            GridLayoutManager(this, 4)
        rv_upload_filter.setHasFixedSize(true)

        rv_upload_filter.adapter = UploadFilterAdapter(filterList, themes, saveFilterList)
    }

    private fun filterAdd() {
        filterList.add(RecipeDTO.Themes(21, "든든"))
        filterList.add(RecipeDTO.Themes(22,"간편"))
        filterList.add(RecipeDTO.Themes(22, "분위기"))
        filterList.add(RecipeDTO.Themes(24, "혼밥"))
        filterList.add(RecipeDTO.Themes(25, "간식"))
        filterList.add(RecipeDTO.Themes(26, "술안주"))
        filterList.add(RecipeDTO.Themes(27, "굽기"))
        filterList.add(RecipeDTO.Themes(28, "파티"))
        filterList.add(RecipeDTO.Themes(29, "베이킹"))
    }

    private fun textWatcher() {
        et_recipe_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                recipeTitle = p0.toString()
            }
        })

        et_recipe_subtitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                subTitle = p0.toString()
            }
        })
    }

    private fun checkPermissionNextButton(): Boolean {
        Log.d("filter", themes[0].name.toString() + " " + themes[1].name.toString() + " ")
        if (recipeTitle.isNotEmpty() && saveFilterList.size > 0 && subTitle.isNotEmpty()) {
            return true
        } else if (saveFilterList.size < 0) {
            Toast.makeText(this, "필터를 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        } else if (subTitle.isEmpty()) {
            Toast.makeText(this, "레시피를 간단하게 설명해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (recipeTitle.isEmpty()) {
            Toast.makeText(this, "레시피 제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        } else {
            Toast.makeText(this, "항목을 채워주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        return false
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
}