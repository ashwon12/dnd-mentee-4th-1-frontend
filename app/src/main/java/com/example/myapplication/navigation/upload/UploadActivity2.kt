package com.example.myapplication.navigation.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.skyhope.materialtagview.enums.TagSeparator
import com.skyhope.materialtagview.interfaces.TagItemListener
import com.skyhope.materialtagview.model.TagModel
import kotlinx.android.synthetic.main.activity_upload2.*

class UploadActivity2 : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle: String? = null
    private var saveFilterList = ArrayList<String>()
    private val REQUEST_GALLERY_CODE = 100
    private var thumbnail: Uri? = null
    private lateinit var tagModel : MutableList<TagModel>
    private lateinit var tagModel2 : MutableList<TagModel>
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

        initMainFoodTagView()
        initSubFoodTagView()
        getItems()

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

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }

    private fun clickNextButton() {
        makeTagList()

        val intent = Intent(this, UploadActivity3::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        startActivity(intent)
    }

    private fun initMainFoodTagView() {
        val listener : TagItemListener? = null
        tagView_mainfood.initTagListener(listener)
        tagView_mainfood.setTagList()
        tagView_mainfood.setHint("재료를 쓰고 ,를 입력하거나 아래 버튼 클릭")
        tagModel = tagView_mainfood.selectedTags
    }

    private fun initSubFoodTagView() {
        val listener2 : TagItemListener? = null
        tagView_subfood.initTagListener(listener2)
        tagView_subfood.setTagList()
        tagView_subfood.setHint("재료를 쓰고 ,를 입력하거나 아래 버튼 클릭")
        tagModel2 = tagView_subfood.selectedTags
    }

    private fun makeTagList() {
        if(tagModel != null){
            for(i in tagModel.indices) {
                if(tagModel[i].tagText.toString() != "") {
                    mainFoodTagList.add(tagModel[i].tagText.toString())
                }
            }
        }

        if(tagModel2 != null) {
            for(i in tagModel2.indices) {
                if(tagModel[i].tagText.toString() != "") {
                    subFoodTagList.add(tagModel2[i].tagText.toString())
                }
            }
        }

    }
}


