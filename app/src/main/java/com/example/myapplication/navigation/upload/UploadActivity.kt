package com.example.myapplication.navigation.upload

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle : String? = null
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var saveFilterList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        textWatcher()
        iv_three_cut.setOnClickListener { clickThreeCut() }
        iv_six_cut.setOnClickListener { clickSixCut() }
        iv_nine_cut.setOnClickListener { clickNineCut() }
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
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
        filterList.add(RecipeDTO.Filter("#(해시태그)"))
    }
    private fun enableButton() {
        btn_upload_recipe_next1.isEnabled = true

        if(btn_upload_recipe_next1.isEnabled == true) {
            btn_upload_recipe_next1.setBackgroundColor(Color.parseColor("#FF8C4B"))
            btn_upload_recipe_next1.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }
    private fun clickThreeCut() {
        select_cut = 3
        iv_three_cut.setImageResource(R.drawable.ic_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_no_select_cut)
        enableButton()
    }

    private fun clickSixCut() {
        select_cut = 6
        iv_three_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_no_select_cut)
        enableButton()
    }

    private fun clickNineCut() {
        select_cut = 9
        iv_three_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_six_cut.setImageResource(R.drawable.ic_no_select_cut)
        iv_nine_cut.setImageResource(R.drawable.ic_select_cut)
        enableButton()
    }

    private fun clickNextButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun textWatcher() {
        et_recipe_title.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                recipeTitle = p0.toString()
            }
        })
    }
}