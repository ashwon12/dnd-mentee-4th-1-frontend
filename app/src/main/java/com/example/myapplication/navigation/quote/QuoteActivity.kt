package com.example.myapplication.navigation.quote

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_quote.*

class QuoteActivity : AppCompatActivity() {
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var select_cut: Int = 0
    private var recipeTitle: String? = null
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var saveFilterList = ArrayList<String>()
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var thumbnail: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        getItems()

        callAdapter()
    }

    private fun callAdapter() {
        rv_quote_filter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_quote_filter.setHasFixedSize(true)

        rv_quote_filter.adapter = QuoteFilterAdapter(filterList)
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
        if (intent.hasExtra("thumbnail")) {
            thumbnail = intent.getParcelableExtra("thumbnail")
            setThumbnail()
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
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            tv_quote_recipe_title.text = recipeTitle
        }
    }

    private fun setThumbnail() {
        Glide.with(this)
            .load(thumbnail)
            .into(iv_quote_recipe_poster)
    }
}