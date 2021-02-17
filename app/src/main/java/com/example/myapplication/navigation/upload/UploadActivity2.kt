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
            "60분",
            "70분",
            "80분",
            "90분",
            "100분",
            "110분",
            "120분",
            "130분",
            "140분",
            "150분",
            "160분",
            "170분",
            "180분",
            "190분",
            "200분",
            "210분",
            "220분",
            "230분",
            "240분",
            "end"
        )
        val minute: NumberPicker = mView.findViewById(R.id.min_picker)
        val cancel: Button = mView.findViewById(R.id.btn_time_cancel)
        val submit: Button = mView.findViewById(R.id.btn_time_submit)

        minute.apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = 0
            maxValue = step.size - 2
            displayedValues = step
        }

        cancel.setOnClickListener {
            tv_upload_time_set_value.text = "0분"
            dialog.dismiss()
            dialog.cancel()
        }

        submit.setOnClickListener {
            Log.d("submit", "submit")
            tv_upload_time_set_value.text = step[minute.value]

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


