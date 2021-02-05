package com.example.myapplication.navigation.upload

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    private var select_cut: Int = 0
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var saveFilterList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        iv_three_cut.setOnClickListener { clickThreeCut() }
        iv_six_cut.setOnClickListener { clickSixCut() }
        iv_nine_cut.setOnClickListener { clickNineCut() }
        btn_upload_recipe_next1.setOnClickListener { clickNextButton() }

        filterAdd()

        callAdapter()
    }

    private fun callAdapter() {
        rv_upload_filter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_upload_filter.setHasFixedSize(true)

        rv_upload_filter.adapter = UploadFilterAdapter(filterList, saveFilterList)
    }

    private fun filterAdd() {
        filterList.add(RecipeDTO.Filter("test1"))
        filterList.add(RecipeDTO.Filter("test2"))
        filterList.add(RecipeDTO.Filter("test3"))
        filterList.add(RecipeDTO.Filter("test4"))
        filterList.add(RecipeDTO.Filter("test5"))
        filterList.add(RecipeDTO.Filter("test6"))
        filterList.add(RecipeDTO.Filter("test7"))
    }

    private fun clickThreeCut() {
        select_cut = 3
        iv_three_cut.setImageResource(R.drawable.ic_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_no_select_cut)
        btn_upload_recipe_next1.isEnabled = true
    }

    private fun clickSixCut() {
        select_cut = 6
        iv_three_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_no_select_cut)
        btn_upload_recipe_next1.isEnabled = true
    }

    private fun clickNineCut() {
        select_cut = 9
        iv_three_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_select_cut)
        btn_upload_recipe_next1.isEnabled = true
    }

    private fun clickNextButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}