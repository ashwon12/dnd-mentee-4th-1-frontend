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
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.navigation.quote.QuoteActivity
import kotlinx.android.synthetic.main.activity_upload2.*
import kotlinx.android.synthetic.main.activity_upload4.*
import kotlinx.android.synthetic.main.activity_upload4.iv_upload_cancel

class UploadActivity4 : AppCompatActivity() {
    private var select_cut: Int = 0
    private var recipeTitle : String? = null
    private var subTitle : String? = null
    private var steps = ArrayList<RecipeDTO.Recipe>()
    private var timeString: String = ""
    private var saveFilterList = ArrayList<String>()
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var thumbnail: String? = null
    private var filterList = ArrayList<RecipeDTO.Themes>()
    private lateinit var adapter: UploadPreviewRecipeAdapter

    private lateinit var recipePostResult : RecipeDTO.RecipeFinal
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload4)

        getItems()

        setPageImage()
        btn_upload_recipe_prev2.setOnClickListener {
            clickPrevButton()
        }
        btn_submit.setOnClickListener {
            clickSubmitButton()
        }
        iv_upload_cancel.setOnClickListener {
            clickCancelButton()
        }
    }

    private fun clickPrevButton() {
        val intent = Intent(this, QuoteActivity::class.java )
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("steps", steps)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.putExtra("time", timeString)
        intent.putExtra("subtitle",subTitle)
        startActivity(intent)
        finish()
    }

    private fun clickSubmitButton() {

        val intent = Intent(this, MainActivity::class.java )
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("steps", steps)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.putExtra("time", timeString)
        intent.putExtra("subtitle",subTitle)
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
            // select_cut = intent.getIntExtra("number", 1)
            recipePostResult.viewCount = intent.getIntExtra("number", 1).toString()
            Log.d("select_cut", recipePostResult.viewCount.toString())
        }
        if (intent.hasExtra("filter")) {
            // saveFilterList = intent.getStringArrayListExtra("filter")!!
            recipePostResult.themes = intent.getSerializableExtra("filter") as Array<RecipeDTO.Themes>
            Log.d("savefilterList", recipePostResult.themes.toString())
        }
        if (intent.hasExtra("thumbnail")) {
            thumbnail = intent.getStringExtra("thumbnail")
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
        if (intent.hasExtra("steps")) {
            steps = intent.getSerializableExtra("steps") as ArrayList<RecipeDTO.Recipe>
            setPageImage()
            Log.d("steps", steps.toString())
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            Log.d("recipeTitle", recipeTitle.toString())
        }
        if(intent.hasExtra("time")) {
            timeString = intent.getStringExtra("time")!!
            Log.d("time", timeString)
        }
        if (intent.hasExtra("subtitle")) {
            subTitle = intent.getStringExtra("subtitle")!!
            Log.d("subTitle", subTitle.toString())
        }
    }


    private fun setPageImage() {
        var rv_recipe_list = findViewById(R.id.rv_upload_preview_recipe) as RecyclerView
        adapter = UploadPreviewRecipeAdapter(steps)
        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_recipe_list.setHasFixedSize(true)
    }

    private fun postRecipeUpload() {
        repository.postRecipeUpload(recipePostResult, success = {

        }, fail = {

        })
    }
}