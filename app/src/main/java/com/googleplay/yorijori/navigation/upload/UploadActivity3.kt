package com.googleplay.yorijori.navigation.upload

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.App
import com.googleplay.yorijori.MainActivity
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import com.googleplay.yorijori.data.repository.Repository
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.utils.PathUtils
import kotlinx.android.synthetic.main.activity_upload3.*

class UploadActivity3 : AppCompatActivity() {

    companion object {
        private const val REQUEST_GET_IMAGE = 105
        private const val PERMISSION_CODE = 100
    }

    private var themes = ArrayList<RecipeDTO.Themes>()
    private var timeString: String = ""
    private var number = 1
    private var count = 0
    private var recipeTitle: String? = null
    private var subTitle: String? = null
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var commentList = ArrayList<RecipeDTO.Recipe>()
    private var saveFilterList = ArrayList<String>()
    private var thumbnail: String? = null
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var positionMain = 0
    private var steps = ArrayList<RecipeDTO.Recipe>()

    private lateinit var adapter: UploadRecipeAdapter
    private lateinit var commentAdapter: UploadCommentAdapter
    private lateinit var itemMain: RecipeDTO.Recipe

    private var saveImages = RecipeDTO.UploadImage("", "", "", "", "", "")
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload3)

        setStatusBarTransparent()

        callRecycler()

        getItems()

        makeRecyclerView()

        itemTouch()

        btn_upload_add_comment.setOnClickListener {
            addButtonClick()
        }
        btn_upload_recipe_prev2.setOnClickListener {
            clickPrevButton()
        }
        btn_preview.setOnClickListener {
            // Toast.makeText(this, "개발중", Toast.LENGTH_SHORT).show()
            clickPreviewButton()
        }
        iv_upload_cancel.setOnClickListener {
            clickCancelButton()
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun addButtonClick() {
        Log.d("click", "클릭됨")
        Log.d("commentList", commentList.toString())
        addItem(
            rv_upload_comment.adapter!!.itemCount,
            RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
        )
        addRecyclerView()
        adapter.notifyDataSetChanged()
    }

    private fun itemTouch() {
        val item = object : UploadSwapDelete(this, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (count > 3) {
                    commentAdapter.deleteItem(viewHolder.adapterPosition)
                    Log.d("id", viewHolder.itemId.toString())
                    Log.d("position", viewHolder.adapterPosition.toString())
                    Log.d("direction", direction.toString())
                    Log.d("positionMain", positionMain.toString())
                    Log.d("count", count.toString())
                    adapter.deleteItem(viewHolder.layoutPosition)
                    number--
                    count--
                } else {
                    Toast.makeText(
                        App.instance,
                        "사진은 최소 3장 업로드 해야합니다.\n사진을 눌러 변경해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                    commentAdapter.notifyDataSetChanged()
                }

            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(rv_upload_comment)
    }

    private fun addItem(position: Int, data: RecipeDTO.Recipe) {
        commentList.add(position, data)
        commentAdapter.notifyDataSetChanged()
        count++

    }

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", number)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("title", recipeTitle)
        startActivity(intent)
        finish()
    }

    private fun clickPreviewButton() {
        makeSteps()

        if (checkPermissionNextButton()) {
            val intent = Intent(App.instance, UploadActivity4::class.java)
            intent.putExtra("recipeTitle", recipeTitle)
            intent.putExtra("filter", saveFilterList)
            intent.putExtra("steps", steps)
            intent.putExtra("mainfood", mainFoodTagList)
            intent.putExtra("subfood", subFoodTagList)
            intent.putExtra("thumbnail", thumbnail)
            intent.putExtra("number", count.toString())
            Log.d("number", count.toString() + "count here!!")
            intent.putExtra("time", timeString)
            intent.putExtra("subtitle", subTitle)
            intent.putExtra("themes", themes)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

    }

    private fun clickCancelButton() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(
            "나중에 올릴 땐 다시 작성해야해요\n" +
                    "작성을 멈추시겠어요?"
        )
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("cancel", "1")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun makeRecyclerView() {
        recipeList.clear()
        recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))

        for (i in 0..2) {
            number++
            recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
            addItem(
                rv_upload_comment.adapter!!.itemCount,
                RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
            )
        }

        Log.d("RecyclerView number", number.toString())
    }

    private fun addRecyclerView() {
        if (number >= 3) {
            number++
            recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
        }
        Log.d("RecyclerView number", number.toString())
        Log.d("recipeList", recipeList.toString())
    }

    private fun getItems() {
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            Log.d("savefilterList", saveFilterList.toString())
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
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            Log.d("title", recipeTitle.toString())
        }
        if (intent.hasExtra("time")) {
            timeString = intent.getStringExtra("time")!!
            Log.d("time", timeString)
        }
        if (intent.hasExtra("subtitle")) {
            subTitle = intent.getStringExtra("subtitle")!!
            Log.d("subTitle", subTitle.toString())
        }
        if (intent.hasExtra("themes")) {
            themes = intent.getSerializableExtra("themes") as ArrayList<RecipeDTO.Themes>
            for (i in themes.indices) {
                Log.d("themes", themes[i].id.toString() + " " + themes[i].name.toString() + "여기")
            }
        }
    }

    private fun callRecycler() {
        var rv_recipe_list = findViewById(R.id.rv_upload_image) as RecyclerView
        adapter = UploadRecipeAdapter(recipeList) { position, item ->
            positionMain = position
            itemMain = item

            checkPermissions()
        }

        adapter.onItemLongClick = { position, item ->
            positionMain = position
            itemMain = item

            if (count > 0) {
                showDialog()
            }
        }

        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_recipe_list.setHasFixedSize(true)

        commentAdapter = UploadCommentAdapter(commentList)
        rv_upload_comment.adapter = commentAdapter
        rv_upload_comment.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_upload_comment.setHasFixedSize(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_GET_IMAGE ->
                    Matisse.obtainResult(data)?.let {
                        val mSelected: List<Uri> = Matisse.obtainResult(data)
                        val size = mSelected.size
                        if (size == 1) {
                            imageUploadToServer(PathUtils.getPath(App.instance, mSelected[0]))
                            if (number >= 3 && positionMain >= number - 1) {
                                addRecyclerView()
                                addItem(
                                    rv_upload_comment.adapter!!.itemCount,
                                    RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
                                )
                            }
                        } else {
                            if (positionMain + size <= number) {
                                for (i in mSelected.indices) {
                                    imageUploadToServer(
                                        PathUtils.getPath(
                                            App.instance,
                                            mSelected[i]
                                        ), i
                                    )
                                }
                            } else if (positionMain + size > number) {
                                for (i in mSelected.indices) {
                                    imageUploadToServer(
                                        PathUtils.getPath(
                                            App.instance,
                                            mSelected[i]
                                        ), i
                                    )
                                    if (positionMain + i >= number - 1) {
                                        addRecyclerView()
                                        addItem(
                                            rv_upload_comment.adapter!!.itemCount,
                                            RecipeDTO.Recipe(
                                                Integer.toString(count + 1),
                                                "",
                                                ""
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                else -> finish()
            }
        }
    }

    private fun pickUpGallery() {
        Matisse.from(this)
            .choose(MimeType.ofImage())
            .countable(true)
            .maxSelectable(9)
            .spanCount(3)
            .imageEngine(GlideEngine())
            .forResult(REQUEST_GET_IMAGE)
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


    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("사진을 삭제하시겠습니까?")
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                Log.d("positionMain", positionMain.toString())
                Log.d("count", count.toString())
                if ((positionMain < count) && count > 3) {
                    adapter.deleteItem(positionMain)
                    commentAdapter.deleteItem(positionMain)
                    if (positionMain == 4) {
                        addRecyclerView()
                    }
                    count--
                    number--
                } else {
                    if (count <= 3) {
                        Toast.makeText(
                            this,
                            "사진은 최소 3장 업로드 해야합니다.\n사진을 눌러 변경해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, "삭제하실 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }

                }
                Log.d("dialog_number", number.toString())
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun makeSteps() {
        steps.clear()

        for (i in commentList.indices) {
            steps.add(
                RecipeDTO.Recipe(
                    commentList[i].number,
                    commentList[i].comment,
                    recipeList[i].image
                )
            )
        }

        Log.d("steps", steps.toString())
    }


    private fun imageUploadToServer(imagePath: String) {

        repository.postImageUpload(imagePath,
            success = {
                saveImages.data = it.data
                recipeList[positionMain].image = it.data
                adapter.notifyDataSetChanged()
                Log.d("recipeList here1!!", recipeList[positionMain].image.toString())
            },
            fail = {
                Log.d("function fail", "fail")
            }
        )
    }

    private fun imageUploadToServer(imagePath: String, position: Int) {
        repository.postImageUpload(imagePath,
            success = {
                saveImages.data = it.data
                recipeList[positionMain + position].image = it.data
                adapter.notifyDataSetChanged()
                Log.d("recipeList here2!!", recipeList.toString())
            },
            fail = {
                Log.d("function fail", "fail")
            }
        )
    }

    private fun checkPermissionNextButton(): Boolean {
        if (steps.size < 3) {
            Toast.makeText(this, "최소 3장의 사진을 올려야합니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        for (i in steps.indices) {
            Log.d("comment", steps[i].comment!!.length.toString())
            if (steps[i].comment == "") {
                Toast.makeText(this, "설명이 다 채워지지 않았습니다.", Toast.LENGTH_SHORT).show()
                return false
            }
            if (steps[i].comment!!.length < 10) {
                Toast.makeText(this, "설명을 최소 10자 이상 채워주세요.", Toast.LENGTH_SHORT).show()
                return false
            }
            if (steps[i].image == null) {
                Toast.makeText(this, "사진이 다 채워지지 않았습니다.", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
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
}