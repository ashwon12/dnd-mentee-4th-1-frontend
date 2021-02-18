package com.example.myapplication.navigation.upload

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.skyhope.materialtagview.interfaces.TagItemListener
import com.skyhope.materialtagview.model.TagModel
import kotlinx.android.synthetic.main.activity_upload2.*

class UploadActivity2 : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle: String? = null
    private var saveFilterList = ArrayList<String>()
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var timeList = ArrayList<RecipeDTO.Time>()
    private var timeString: String? = null
    private val REQUEST_GALLERY_CODE = 100
    private var thumbnail: Uri? = null
    private lateinit var tagModel: MutableList<TagModel>
    private lateinit var tagModel2: MutableList<TagModel>
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var positionMain = -1
    private lateinit var adapter: UploadTimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

        initMainFoodTagView()
        initSubFoodTagView()
        getItems()
        addTimeFilter()
        callAdapter()

        iv_upload_gallery.setOnClickListener {
            pickFromGallery()
        }

        btn_upload_recipe_prev1.setOnClickListener {
            clickPrevButton()
        }

        btn_upload_recipe_next2.setOnClickListener {
            clickNextButton()
        }
    }

    private fun getItems() {
        if (intent.hasExtra("number")) {
            select_cut = intent.getIntExtra("number", 1)
        }
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            Log.d("savefilterList", saveFilterList.toString())
        }
        if (intent.hasExtra("originFilter")) {
            filterList = intent.getSerializableExtra("originFilter") as ArrayList<RecipeDTO.Filter>
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            Log.d("title", recipeTitle.toString())
        }

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
        if (tagModel != null) {
            for (i in tagModel.indices) {
                if (tagModel[i].tagText.toString() != "") {
                    mainFoodTagList.add(tagModel[i].tagText.toString().replace(" ", ""))
                }
            }
        }

        if (tagModel2 != null) {
            for (i in tagModel2.indices) {
                if (tagModel[i].tagText.toString() != "") {
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

            Log.d("mainmain", "main")
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

            dialog.dismiss()
            dialog.cancel()
        }

        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }

    private fun clickNextButton() {
        makeTagList()

        val intent = Intent(this, UploadActivity3::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("originFilter", filterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        startActivity(intent)
    }
}


