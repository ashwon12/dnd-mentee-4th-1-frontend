package com.example.myapplication.navigation.upload

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.skyhope.materialtagview.interfaces.TagItemListener
import com.skyhope.materialtagview.model.TagModel
import kotlinx.android.synthetic.main.activity_upload2.*

class UploadActivity2 : AppCompatActivity() {
    companion object {
        private const val REQUEST_GALLERY_CODE = 100
        private const val PERMISSION_CODE = 100
    }
    private var recipeTitle: String = ""
    private var saveFilterList = ArrayList<String>()
    private var timeList = ArrayList<RecipeDTO.Time>()
    private var timeString: String = ""
    private var thumbnail: Uri? = null
    private lateinit var tagModel: MutableList<TagModel>
    private lateinit var tagModel2: MutableList<TagModel>
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var positionMain = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

        setStatusBarTransparent()

        initMainFoodTagView()
        initSubFoodTagView()
        getItems()
        addTimeFilter()
        callAdapter()

        iv_upload_gallery.setOnClickListener {
            checkPermissions()
        }

        btn_upload_recipe_prev1.setOnClickListener {
            clickPrevButton()
        }

        btn_upload_recipe_next2.setOnClickListener {
            clickNextButton()
        }

        iv_upload_cancel.setOnClickListener {
            clickCancelButton()
        }
    }

    private fun getItems() {
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")!!
            Log.d("title", recipeTitle.toString())
        }
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            Log.d("savefilterList", saveFilterList.toString())
        }
    }

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clickNextButton() {
        makeTagList()

        if(checkPermissionNextButton()) {
            val intent = Intent(this, UploadActivity3::class.java)
            intent.putExtra("recipeTitle", recipeTitle)
            intent.putExtra("filter", saveFilterList)
            intent.putExtra("thumbnail", thumbnail)
            intent.putExtra("mainfood", mainFoodTagList)
            intent.putExtra("subfood", subFoodTagList)
            intent.putExtra("time", timeString)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            startActivity(intent)
        }
    }

    private fun clickCancelButton() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("나중에 올릴 땐 다시 작성해야해요\n" +
                "작성을 멈추시겠어요?")
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    thumbnail = data?.data
                    try {
                        Glide.with(App.instance)
                            .load(thumbnail)
                            .placeholder(R.drawable.gallery)
                            .into(iv_upload_gallery)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun pickFromGallery() {
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            pickFromGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery()
                } else {
                    Toast.makeText(App.instance, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    private fun initMainFoodTagView() {
        val listener: TagItemListener? = null
        tagView_mainfood.initTagListener(listener)
        tagView_mainfood.setTagList()
        tagView_mainfood.setHint("밀가루4컵, 당근2개")
        tagModel = tagView_mainfood.selectedTags
    }

    private fun initSubFoodTagView() {
        val listener2: TagItemListener? = null
        tagView_subfood.initTagListener(listener2)
        tagView_subfood.setTagList()
        tagView_subfood.setHint("밀가루4컵, 당근2개")
        tagModel2 = tagView_subfood.selectedTags
    }

    private fun makeTagList() {
        mainFoodTagList.clear()
        subFoodTagList.clear()
        
        if (tagModel != null) {
            for (i in tagModel.indices) {
                if (tagModel[i].tagText.toString() != "") {
                    mainFoodTagList.add(tagModel[i].tagText.toString().replace(" ", ""))
                }
            }
        }

        if (tagModel2 != null) {
            for (i in tagModel2.indices) {
                if (tagModel2[i].tagText.toString() != "") {
                    subFoodTagList.add(tagModel2[i].tagText.toString().replace(" ", ""))
                }
            }
        }
    }

    private fun callAdapter() {
        rv_upload_time.layoutManager =
            GridLayoutManager(this, 4)
        rv_upload_time.setHasFixedSize(true)

        rv_upload_time.adapter = UploadTimeAdapter(timeList, timeString) { position ->
            positionMain = position

            if (position == timeList.size - 1) {
                tv_upload_time_set_text.visibility = View.VISIBLE
                tv_upload_time_set_value.visibility = View.VISIBLE
                funTimePicker()
            } else {
                tv_upload_time_set_value.text = "0분"
                tv_upload_time_set_text.visibility = View.INVISIBLE
                tv_upload_time_set_value.visibility = View.INVISIBLE
            }

            timeString = timeList[positionMain].timeName.substring(0,2)
            if(Integer.parseInt(timeString) == 60) {
                timeString = "59"
            }
            Log.d("timeString adapter", timeString)
        }
    }

    private fun addTimeFilter() {
        timeList.add(RecipeDTO.Time("15분 이내"))
        timeList.add(RecipeDTO.Time("30분 이내"))
        timeList.add(RecipeDTO.Time("45분 이내"))
        timeList.add(RecipeDTO.Time("60분 이내"))
        timeList.add(RecipeDTO.Time("60분 이상"))
    }

    private fun funTimePicker() {
        val dialog = AlertDialog.Builder(UploadActivity2@ this).create()
        val edialog: LayoutInflater = LayoutInflater.from(UploadActivity2@ this)
        val mView: View = edialog.inflate(R.layout.dialog_datepicker, null)
        val step: Array<String> = arrayOf(
            "1시간",
            "2시간",
            "3시간",
            "4시간",
            "5시간",
            "6시간",
            "7시간",
            "8시간",
            "9시간",
            "10시간",
            "11시간",
            "12시간",
            "13시간",
            "14시간",
            "15시간",
            "16시간",
            "17시간",
            "18시간",
            "19시간",
            "20시간",
            "21시간",
            "22시간",
            "23시간"
        )
        val step2: Array<String> = arrayOf(
            "0분",
            "1분",
            "2분",
            "3분",
            "4분",
            "5분",
            "6분",
            "7분",
            "8분",
            "9분",
            "10분",
            "11분",
            "12분",
            "13분",
            "14분",
            "15분",
            "16분",
            "17분",
            "18분",
            "19분",
            "20분",
            "21분",
            "22분",
            "23분",
            "24분",
            "25분",
            "26분",
            "27분",
            "28분",
            "29분",
            "30분",
            "31분",
            "32분",
            "33분",
            "34분",
            "35분",
            "36분",
            "37분",
            "38분",
            "39분",
            "40분",
            "41분",
            "42분",
            "43분",
            "44분",
            "45분",
            "46분",
            "47분",
            "48분",
            "49분",
            "50분",
            "51분",
            "52분",
            "53분",
            "54분",
            "55분",
            "56분",
            "57분",
            "58분",
            "59분"
        )

        val minute: NumberPicker = mView.findViewById(R.id.min_picker)
        val hour: NumberPicker = mView.findViewById(R.id.hour_picker)
        val cancel: Button = mView.findViewById(R.id.btn_time_cancel)
        val submit: Button = mView.findViewById(R.id.btn_time_submit)

        hour.apply {
            wrapSelectorWheel = true
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = 0
            maxValue = 22
            displayedValues = step
        }
        minute.apply {
            wrapSelectorWheel = true
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = 0
            maxValue = 59
            displayedValues = step2
        }

        cancel.setOnClickListener {
            tv_upload_time_set_value.text = "0분"
            dialog.dismiss()
            dialog.cancel()
        }

        submit.setOnClickListener {
            Log.d("submit", "submit")
            tv_upload_time_set_value.text = step[hour.value] + " " + step2[minute.value]

            var hour_time = step[hour.value]
            var min_time = step2[minute.value]
            var hour_len = step[hour.value].length
            var min_len = step2[minute.value].length

            val h = hour_time.substring(0,hour_len-2)
            val m = min_time.substring(0,min_len-1)

            Log.d("hour_time", hour_len.toString())
            Log.d("min_time", min_len.toString())


            timeString = ((Integer.parseInt(h) * 60) + Integer.parseInt(m)).toString()
            Log.d("timeString here", timeString)
            dialog.dismiss()
            dialog.cancel()
        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }

    private fun checkPermissionNextButton(): Boolean {
        Log.d("timeString", timeString + "여기")
        
        if (thumbnail != null && mainFoodTagList.size > 0 && subFoodTagList.size > 0 && timeString != "") {
            return true
        } else if (mainFoodTagList.size > 0 && subFoodTagList.size > 0 && timeString != "") {
            Toast.makeText(this, "레시피 썸네일을 등록해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (thumbnail != null && subFoodTagList.size > 0 && timeString != "") {
            Toast.makeText(this, "요리 필수 재료를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (thumbnail != null && mainFoodTagList.size > 0 && timeString != "") {
            Toast.makeText(this, "요리 부가 재료를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        } else if (thumbnail != null && mainFoodTagList.size > 0 && subFoodTagList.size > 0) {
            Toast.makeText(this, "얼마나 걸리는지 시간을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        Toast.makeText(this, "항목을 채워주세요", Toast.LENGTH_SHORT).show()

        return false
    }

    fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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


