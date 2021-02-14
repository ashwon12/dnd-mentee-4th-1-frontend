package com.example.myapplication.navigation.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload4.*

class UploadActivity4 : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle : String? = null
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var saveFilterList = ArrayList<String>()
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var thumbnail: Uri? = null
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private lateinit var adapter: UploadPreviewRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload4)

        getItems()

        btn_submit.setOnClickListener {
            Toast.makeText(this, "서버 전송 미완성", Toast.LENGTH_SHORT).show()
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
    }

    private fun getItems() {
        if (intent.hasExtra("number")) {
            select_cut = intent.getIntExtra("number", 1)
            setPageCut()
        }
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            setPageFilter()
            Log.d("savefilterList", saveFilterList.toString())
        }
        if (intent.hasExtra("thumbnail")) {
            thumbnail = intent.getParcelableExtra("thumbnail")
            Log.d("thumbnail", thumbnail.toString())
        }
        if (intent.hasExtra("mainfood")) {
            mainFoodTagList = intent.getStringArrayListExtra("mainfood")!!
            Log.d("mainfood", mainFoodTagList.toString())
        }
        if (intent.hasExtra("subfood")) {
            subFoodTagList = intent.getStringArrayListExtra("subfood")!!
            Log.d("subfood", subFoodTagList.toString())
        }
        if (intent.hasExtra("recipeList")) {
            recipeList = intent.getSerializableExtra("recipeList") as ArrayList<RecipeDTO.Recipe>
            setPageImage()
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
        }
    }

    private fun setPageCut() {
        when (select_cut) {
            3 -> iv_three_cut2.setImageResource(R.drawable.ic_select_cut)
            6 -> iv_six_cut2.setImageResource(R.drawable.ic_select_cut)
            9 -> iv_nine_cut2.setImageResource(R.drawable.ic_select_cut)
        }
    }

    private fun setPageFilter() {
        addFilter()

        rv_upload_filter2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_upload_filter2.setHasFixedSize(true)

        rv_upload_filter2.adapter = UploadPreviewFilterAdapter(filterList)
    }

    private fun setPageImage() {
        var rv_recipe_list = findViewById(R.id.rv_upload_recipe2) as RecyclerView
        adapter = UploadPreviewRecipeAdapter(recipeList)
        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_recipe_list.setHasFixedSize(true)
    }

    private fun addFilter() {
        for(i in saveFilterList.indices) {
            filterList.add(RecipeDTO.Filter(saveFilterList[i]))
            Log.d("filter", filterList.toString())
        }
    }



}