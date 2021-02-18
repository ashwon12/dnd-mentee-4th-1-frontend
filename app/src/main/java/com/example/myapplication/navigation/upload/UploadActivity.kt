package com.example.myapplication.navigation.upload

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle: String? = null
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var saveFilterList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        textWatcher()

        btn_upload_recipe_next1.setOnClickListener { clickNextButton() }

        filterAdd()

        callAdapter()
    }

    private fun callAdapter() {
        rv_upload_filter.layoutManager =
            GridLayoutManager(this, 4)
        rv_upload_filter.setHasFixedSize(true)

        rv_upload_filter.adapter = UploadFilterAdapter(filterList, saveFilterList)
    }

    private fun filterAdd() {
        filterList.add(RecipeDTO.Filter("#3컷요리"))
        filterList.add(RecipeDTO.Filter("#6컷요리"))
        filterList.add(RecipeDTO.Filter("#9컷요리"))
        filterList.add(RecipeDTO.Filter("#매운맛"))
        filterList.add(RecipeDTO.Filter("#단짠단짠"))
        filterList.add(RecipeDTO.Filter("#홈파티"))
        filterList.add(RecipeDTO.Filter("#야식"))
    }


    private fun clickNextButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("originFilter", filterList)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun textWatcher() {
        et_recipe_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                recipeTitle = p0.toString()
            }
        })
    }
}