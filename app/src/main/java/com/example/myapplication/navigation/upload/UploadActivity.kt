package com.example.myapplication.navigation.upload

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    var select_cut: Int = 0
    var filterList = arrayListOf<RecipeDTO.Filter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        iv_three_cut.setOnClickListener { clickThreeCut() }
        iv_six_cut.setOnClickListener { clickSixCut() }
        iv_nine_cut.setOnClickListener { clickNineCut() }
        btn_upload_recipe_next1.setOnClickListener { clickNextButton() }

        filterAdd()

        rv_upload_filter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_upload_filter.setHasFixedSize(true)

        rv_upload_filter.adapter = UploadFilterAdpater(filterList)
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

    private fun clickThreeCut(){
        Toast.makeText(this, "click_three_cut", Toast.LENGTH_SHORT).show()
        select_cut = 3
        iv_three_cut.setImageResource(R.drawable.ic_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_no_select_cut)
        btn_upload_recipe_next1.isEnabled = true
    }

    private fun clickSixCut(){
        Toast.makeText(this, "click_six_cut", Toast.LENGTH_SHORT).show()
        select_cut = 6
        iv_three_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_no_select_cut)
        btn_upload_recipe_next1.isEnabled = true
    }

    private fun clickNineCut(){
        Toast.makeText(this, "click_nine_cut", Toast.LENGTH_SHORT).show()
        select_cut = 9
        iv_three_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_select_cut)
        btn_upload_recipe_next1.isEnabled = true
    }

    private fun clickNextButton(){
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", select_cut)
        startActivity(intent)
        finish()
    }
}