package com.example.myapplication.navigation.upload

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.navigation.quote.QuoteActivity
import kotlinx.android.synthetic.main.activity_upload4.*
import org.json.JSONObject

class UploadActivity4 : AppCompatActivity() {
    private var data = HashMap<String, Any>()
    private var themes = ArrayList<RecipeDTO.Themes>()
    private var select_cut: String? = null
    private var recipeTitle: String? = null
    private var subTitle: String? = null
    private var steps = ArrayList<RecipeDTO.Recipe>()
    private var timeString: String = ""
    private var saveFilterList = ArrayList<String>()
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var thumbnail: String? = null
    private var filterList = ArrayList<RecipeDTO.Themes>()
    private lateinit var adapter: UploadPreviewRecipeAdapter
    private var obj = JSONObject()
    val recipeResult = RecipeDTO.UploadRecipe(null, null, null, ArrayList<RecipeDTO.MainIngredients>(),ArrayList<RecipeDTO.SubIngredients>(), ArrayList<Int>(), ArrayList<RecipeDTO.Steps>(),null,null,null,null)
    val recipePostResult = RecipeDTO.APIResponseData(
        null, null, null, null, null,
        RecipeDTO.RecipeFinal(null, "", "", "",
            ArrayList<RecipeDTO.MainIngredients>(),ArrayList<RecipeDTO.SubIngredients>(), null, null, null, "", "", "", null, null)
    )
    // private lateinit var recipePostResult : RecipeDTO.APIResponseData

    private val repository = Repository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload4)

        setStatusBarTransparent()

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
        val intent = Intent(this, QuoteActivity::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("steps", steps)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.putExtra("time", timeString)
        intent.putExtra("subtitle", subTitle)
        startActivity(intent)
        finish()
    }

    private fun clickSubmitButton() {
        setResult()
        
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("steps", steps)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.putExtra("time", timeString)
        intent.putExtra("subtitle", subTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun clickCancelButton() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(
            "나중에 올릴 땐 다시 작성해야해요\n" +
                    "작성을 멈추시겠어요?"
        )
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
            select_cut = intent.getStringExtra("number")
        }
        if (intent.hasExtra("themes")) {
            themes = intent.getSerializableExtra("themes") as ArrayList<RecipeDTO.Themes>
        }
        if (intent.hasExtra("thumbnail")) {
            thumbnail = intent.getStringExtra("thumbnail")
        }
        if (intent.hasExtra("mainfood")) {
            mainFoodTagList = intent.getStringArrayListExtra("mainfood")!!
        }
        if (intent.hasExtra("subfood")) {
            subFoodTagList = intent.getStringArrayListExtra("subfood")!!
        }
        if (intent.hasExtra("steps")) {
            steps = intent.getSerializableExtra("steps") as ArrayList<RecipeDTO.Recipe>
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
        }
        if (intent.hasExtra("time")) {
            timeString = intent.getStringExtra("time")!!
        }
        if (intent.hasExtra("subtitle")) {
            subTitle = intent.getStringExtra("subtitle")!!
        }
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
        }
    }

    private fun setResult() {
        recipeResult.title = recipeTitle.toString()
        recipeResult.description = subTitle.toString()
        recipeResult.thumbnail = thumbnail.toString()
        recipeResult.writerId = 1
        recipeResult.time = timeString
        recipeResult.viewCount = select_cut

        for (i in mainFoodTagList.indices) {
            recipeResult.mainIngredients?.add(RecipeDTO.MainIngredients(mainFoodTagList[i]))
        }

        for (i in subFoodTagList.indices) {
            recipeResult.subIngredients?.add(RecipeDTO.SubIngredients(subFoodTagList[i]))
        }

        for(i in steps.indices) {
            recipeResult.steps?.add(RecipeDTO.Steps(null, steps[i].comment, steps[i].image, null))
        }

        for(i in themes.indices) {
            recipeResult.themeIds?.add(themes[i].id!!)
        }
        recipeResult.pid = 1

        recipeUpload()
    }

    private fun setPageImage() {
        var rv_recipe_list = findViewById(R.id.rv_upload_preview_recipe) as RecyclerView
        adapter = UploadPreviewRecipeAdapter(steps)
        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_recipe_list.setHasFixedSize(true)
    }

    /**  StatusBar 투명하게 만들기(1)  **/
    fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**  StatusBar 투명하게 만들기(2)  **/
    fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun recipeUpload() {
        repository.postRecipeUpload(recipeResult,
            success = {
                Log.d("success", "success")
            }, fail = {
                Log.d("function fail", "fail")
            })
    }
}