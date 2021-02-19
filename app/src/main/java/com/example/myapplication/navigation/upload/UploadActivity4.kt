package com.example.myapplication.navigation.upload

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.navigation.quote.QuoteActivity
import kotlinx.android.synthetic.main.activity_upload2.*
import kotlinx.android.synthetic.main.activity_upload4.*
import kotlinx.android.synthetic.main.activity_upload4.iv_upload_cancel

class UploadActivity4 : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle : String? = null
    private var steps = ArrayList<RecipeDTO.Recipe>()
    private var test = ArrayList<RecipeDTO.Recipe>()
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

        test.add(RecipeDTO.Recipe("1","안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","두줄 입력 시\n이렇게 나옵니다.","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        test.add(RecipeDTO.Recipe("1","한줄 입력 시 이렇게 나옵니다.","https://rgo4.com/files/attach/images/2681740/024/470/011/70c03f4555eaf6da08fcd9af5f0fe481.JPG"))
        setPageImage()
        btn_submit.setOnClickListener {
            // Toast.makeText(this, "서버 전송 미완성", Toast.LENGTH_SHORT).show()
            clickSubmitButton()
        }
        iv_upload_cancel.setOnClickListener {
            clickCancelButton()
        }
    }

    private fun clickSubmitButton() {
        val intent = Intent(this, QuoteActivity::class.java )
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("originFilter", filterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("steps", steps)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun clickCancelButton() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("나중에 올릴 땐 다시 작성해야해요\n" +
                "작성을 멈추시겠어요?")
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun getItems() {
        steps.clear()

        if (intent.hasExtra("number")) {
            select_cut = intent.getIntExtra("number", 1)
        }
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            // setPageFilter()
            Log.d("savefilterList", saveFilterList.toString())
        }
        if(intent.hasExtra("originFilter")) {
            filterList = intent.getSerializableExtra("originFilter") as ArrayList<RecipeDTO.Filter>
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
//        if (intent.hasExtra("steps")) {
//            steps = intent.getSerializableExtra("steps") as ArrayList<RecipeDTO.Recipe>
//            setPageImage()
//        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
        }
    }


    private fun setPageImage() {
        var rv_recipe_list = findViewById(R.id.rv_upload_preview_recipe) as RecyclerView
        adapter = UploadPreviewRecipeAdapter(test)
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