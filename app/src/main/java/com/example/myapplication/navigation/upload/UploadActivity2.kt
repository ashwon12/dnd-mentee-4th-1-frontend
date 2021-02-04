package com.example.myapplication.navigation.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload2.*

class UploadActivity2 : AppCompatActivity() {
    private var select_cut: Int = 0
    private var saveFilterList = ArrayList<String>()
    private val REQUEST_GALLERY_CODE = 100
    private var thumbnail: Uri? = null
    private var saveMainFoodList = ArrayList<String>()
    private var saveSubFoodList = ArrayList<String>()
    private var mainFoodString: CharSequence? = null
    private var subFoodString: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

        getItems()
        textWhatcher()

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
        splitString()

        val intent = Intent(this, UploadActivity3::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", saveMainFoodList)
        intent.putExtra("subfood", saveSubFoodList)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun textWhatcher() {
        et_main_food.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                mainFoodString = p0.toString()
            }
        })

        et_sub_food.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                subFoodString = p0
            }
        })
    }

    private fun splitString() {
        if(saveMainFoodList.contains(",")){
            saveMainFoodList = mainFoodString?.split(",") as ArrayList<String>
        } else {
            saveMainFoodList.add(mainFoodString.toString())
        }
        if(saveSubFoodList.contains(",")){
            saveSubFoodList = subFoodString?.split(",") as ArrayList<String>
        } else {
            saveSubFoodList.add(subFoodString.toString())
        }
    }
}


