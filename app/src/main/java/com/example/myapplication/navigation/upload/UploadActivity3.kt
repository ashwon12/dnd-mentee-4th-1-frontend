package com.example.myapplication.navigation.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.activity_upload3.*

class UploadActivity3 : AppCompatActivity() {

    companion object {
        private const val REQUEST_GET_IMAGE = 105
        private const val PERMISSION_CODE = 100
    }

    private var select_cut: Int = 0
    private var number = 1
    private var imageCount = 0
    private var recipeTitle: String? = null
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var saveFilterList = ArrayList<String>()
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var thumbnail: Uri? = null
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var positionMain = 0

    private lateinit var adapter: UploadRecipeAdapter
    private lateinit var itemMain: RecipeDTO.Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload3)

        callRecycler()

        getItems()

        makeRecyclerView()

        btn_upload_recipe_prev2.setOnClickListener {
            clickPrevButton()
        }
        btn_preview.setOnClickListener {
            clickPreviewButton()
        }
    }

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("originFilter", filterList)
        intent.putExtra("title", recipeTitle)
        intent.putExtra("recipeList", recipeList)
        startActivity(intent)
    }

    private fun clickPreviewButton() {
        val intent = Intent(App.instance, UploadActivity4::class.java)
        intent.putExtra("number", select_cut)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("originFilger", filterList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("recipeList", recipeList)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun makeRecyclerView() {
        recipeList.clear()
        select_cut = 0

        recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
        number++
    }

    private fun addRecyclerView() {
        if (number < 10) {
            recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
            number++
        }
    }

    private fun getItems() {
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            Log.d("savefilterList", saveFilterList.toString())
        }
        if (intent.hasExtra("originFilter")) {
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
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            Log.d("title", recipeTitle.toString())
        }
    }

    private fun callRecycler() {
        var rv_recipe_list = findViewById(R.id.rv_upload_image) as RecyclerView
        adapter = UploadRecipeAdapter(recipeList) { position, item ->
            positionMain = position
            itemMain = item

            checkPermissions()
        }

        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_recipe_list.setHasFixedSize(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_GET_IMAGE) {
            when (requestCode) {
                REQUEST_GET_IMAGE ->
                    data?.let {
                        when {
                            it.clipData != null -> {
                                val clip = data?.clipData
                                Log.d("clip", clip.toString())
                                val size = clip!!.itemCount
                                imageCount += size
                                if (size == 1) {
                                    val uri = it?.data
                                    recipeList[positionMain].image = uri.toString()
                                    addRecyclerView()
                                    adapter.notifyDataSetChanged()
                                } else if (imageCount <= 9) {
                                    for (i in size-1 downTo 0) {
                                        recipeList[select_cut].image =
                                            clip?.getItemAt(i)?.uri.toString()
                                        addRecyclerView()
                                        adapter.notifyDataSetChanged()
                                        select_cut++
                                    }
                                } else {
                                    Toast.makeText(
                                        App.instance,
                                        "사진을 9개 이상 전송할 수 없습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    imageCount -= size
                                }
                            }

                            it.data != null -> {
                                val uri = data?.data
                                recipeList[positionMain].image = uri.toString()
                                Log.d("first", recipeList[positionMain].image.toString())
                                adapter.notifyDataSetChanged()
                            }
                        }
                        // showItem()
                    }
            }
        }
    }

    private fun pickUpGallery() {
        val intent = Intent(Intent.ACTION_PICK)

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "gallery"), REQUEST_GET_IMAGE)
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            pickUpGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickUpGallery()
                } else {
                    Toast.makeText(App.instance, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun showItem() {
        for (i in 0..select_cut - 1) {
            Log.d(
                "log",
                "${i} 번째 ,number : " + recipeList.get(i).number + "comment :" + recipeList.get(i).comment.toString() + "image : " + recipeList.get(
                    i
                ).image.toString()
            )
        }
    }
}