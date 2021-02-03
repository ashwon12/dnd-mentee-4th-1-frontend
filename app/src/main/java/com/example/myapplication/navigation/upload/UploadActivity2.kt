package com.example.myapplication.navigation.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_upload2.*

class UploadActivity2 : AppCompatActivity() {
    private var select_cut: Int = 0
    private val REQUEST_GALLERY_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

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

    private fun getItems(){
        if (intent.hasExtra("number")) {
            select_cut = intent.getIntExtra("number", 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_GALLERY_CODE) {
                var imageUrl : Uri? = data?.data

                try{
                    Glide.with(App.instance)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_no_image)
                        .into(iv_upload_gallery)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun pickFromGallery(){
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun clickPrevButton(){
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }

    private fun clickNextButton(){
        val intent = Intent(this, UploadActivity3::class.java)
        intent.putExtra("number", select_cut)
        startActivity(intent)
    }
}


