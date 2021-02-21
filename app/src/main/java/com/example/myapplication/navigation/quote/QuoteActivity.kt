package com.example.myapplication.navigation.quote

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.navigation.upload.UploadSwapDelete
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_quote.*
import java.lang.Exception

class QuoteActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_GALLERY_CODE = 100
        private const val REQUEST_GET_IMAGE = 105
        private const val PERMISSION_CODE = 100
    }

    private var saveCommentList = ArrayList<RecipeDTO.Recipe>()
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var resultCommentList = ArrayList<RecipeDTO.Recipe>()
    private var commentList = ArrayList<RecipeDTO.Recipe>()
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var select_cut: Int = 0
    private var recipeTitle: String? = null
    private var timeString: String = ""
    private var saveFilterList = ArrayList<String>()
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var thumbnail: Uri? = null
    private var steps = ArrayList<RecipeDTO.Recipe>()
    private var number = 0
    private var number1 = 1
    private var count = 0
    private var positionMain = -1
    private var quoteRecipeTitle: String = ""
    private lateinit var itemMain: RecipeDTO.Recipe

    private lateinit var imageAdapter: QuoteImageAdapter
    private lateinit var commentAdapter: QuoteCommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        getItems()
        addFoods()
        addTags()
        callAdapter()
        makeRecyclerView()
        itemTouch()

        btn_quote_add_comment.setOnClickListener {
            addButtonClick()
        }
        iv_upload_cancel.setOnClickListener {
            clickCancelButton()
        }
        btn_quote_submit.setOnClickListener {
            makeSteps()
            checkPermissionNextButton()
        }
        iv_quote_recipe_result.setOnClickListener {
            checkThumbnailsPermissions()
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun makeRecyclerView() {
        for (i in 0..select_cut - 1) {
            recipeList.add(RecipeDTO.Recipe(steps[i].number, null, steps[i].image))
            commentList.add(RecipeDTO.Recipe(steps[i].number, steps[i].comment, null))
            saveCommentList.add(RecipeDTO.Recipe(steps[i].number, steps[i].comment, null))
            resultCommentList.add(RecipeDTO.Recipe(steps[i].number, "", null))
            number++
            count++
        }

        if (select_cut < 9) {
            recipeList.add(RecipeDTO.Recipe((steps.size + 1).toString(), null, null))
            number++
        }
    }

    private fun callAdapter() {
        rv_quote_filter.apply {
            layoutManager =
                GridLayoutManager(App.instance, 3)
            setHasFixedSize(true)

            adapter = QuoteFilterAdapter(filterList)
        }

        var rv_recipe_list = findViewById(R.id.rv_quote_image) as RecyclerView
        imageAdapter = QuoteImageAdapter(recipeList) { position, item ->
            positionMain = position
            itemMain = item

            if (positionMain >= select_cut) {
                checkPermissions()
            } else {
                Toast.makeText(this, "기존 레시피는 변경할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        imageAdapter.onItemLongClick = { position, item ->
            positionMain = position
            itemMain = item

            if (select_cut <= positionMain) {
                showDialog()
            } else {
                Toast.makeText(this, "기존 레시피는 변경할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        rv_recipe_list.apply {
            adapter = imageAdapter
            layoutManager =
                LinearLayoutManager(App.instance, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        commentAdapter = QuoteCommentAdapter(commentList, saveCommentList, select_cut)
        rv_quote_comment.adapter = commentAdapter
        rv_quote_comment.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_quote_comment.setHasFixedSize(true)
    }

    private fun addTags() {
        filterList.clear()

        saveFilterList.add(select_cut.toString() + "컷")

        if (Integer.parseInt(timeString) == 59) {
            saveFilterList.add("60분 이내")
        } else if (Integer.parseInt(timeString) >= 60) {
            saveFilterList.add("60분 이상")
        } else {
            saveFilterList.add(timeString + "분 이내")
        }

        for (i in saveFilterList.indices) {
            filterList.add(RecipeDTO.Filter(saveFilterList[i]))
        }
    }

    private fun addFoods() {
        var food = ""

        for (i in mainFoodTagList.indices) {
            food += mainFoodTagList[i] + ", "
        }
        for (i in subFoodTagList.indices) {
            if (i == subFoodTagList.size - 1) {
                food += subFoodTagList[i]
            } else {
                food += subFoodTagList[i] + ", "
            }
        }

        tv_quote_recipe_food.text = food

    }

    private fun addRecyclerView() {
        if (number < 9 && number > 3) {
            number++
            recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
        }
        //  Log.d("RecyclerView number111", number1.toString())
        Log.d("RecyclerView number", number.toString())
        //  Log.d("recipeList", recipeList.toString())
    }

    private fun addButtonClick() {
        Log.d("count", count.toString())
        if (count < 9) {
            addItem(
                rv_quote_comment.adapter!!.itemCount,
                RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
            )
            addRecyclerView()
            imageAdapter.notifyDataSetChanged()

        } else {
            Toast.makeText(this, "9개 이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun itemTouch() {
        val item = object : UploadSwapDelete(this, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder.adapterPosition >= select_cut) {
                    commentAdapter.deleteItem(viewHolder.adapterPosition)
                    Log.d("id", viewHolder.itemId.toString())
                    Log.d("position", viewHolder.adapterPosition.toString())
                    Log.d("direction", direction.toString())
                    Log.d("positionMain", positionMain.toString())
                    Log.d("count", count.toString())
                    imageAdapter.deleteItem(viewHolder.layoutPosition)
                    number--
                    count--
                    if (viewHolder.layoutPosition == 8) {
                        addRecyclerView()
                    }
                } else {
                    Toast.makeText(App.instance, "기존 레시피는 변경할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    commentAdapter.notifyDataSetChanged()

                }

            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(rv_quote_comment)
    }

    private fun addItem(position: Int, data: RecipeDTO.Recipe) {
        if (count < 9) {
            commentList.add(position, data)
            saveCommentList.add(position, data)
            commentAdapter.notifyDataSetChanged()
            count++
        }
    }

    private fun getItems() {
        if (intent.hasExtra("number")) {
            select_cut = intent.getIntExtra("number", 1)
        }
        if (intent.hasExtra("filter")) {
            saveFilterList = intent.getStringArrayListExtra("filter")!!
            Log.d("savefilterList", saveFilterList.toString())
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
        if (intent.hasExtra("recipeTitle")) {
            recipeTitle = intent.getStringExtra("recipeTitle")
            tv_quote_recipe_title.text = recipeTitle
        }
        if (intent.hasExtra("steps")) {
            steps = intent.getSerializableExtra("steps") as ArrayList<RecipeDTO.Recipe>
            Log.d("steps", steps.toString())
        }
        if (intent.hasExtra("time")) {
            timeString = intent.getStringExtra("time")!!
            Log.d("time", timeString)
        }
    }

    private fun setThumbnail() {
        Glide.with(this)
            .load(thumbnail)
            .into(iv_quote_recipe_poster)
    }

    private fun pickUpGallery() {
        Matisse.from(this)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(9)
            .spanCount(3)
            .imageEngine(GlideEngine())
            .forResult(REQUEST_GET_IMAGE)
    }

    private fun pickUpThumbnailGallery() {
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY_CODE -> {
                    thumbnail = data?.data
                    try {
                        Glide.with(App.instance)
                            .load(thumbnail)
                            .into(iv_quote_recipe_result)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                REQUEST_GET_IMAGE ->
                    Matisse.obtainResult(data)?.let {
                        val mSelected: List<Uri> = Matisse.obtainResult(data)
                        val size = mSelected.size
                        Log.d("positionMain", positionMain.toString())
                        Log.d("size", size.toString())
                        Log.d("inner number", number.toString())
                        if (size == 1) {
                            recipeList[positionMain].image = mSelected[0].toString()
                            addRecyclerView()
                            Log.d("number1", number.toString())
                            if (number > 3 && positionMain >= number - 2) {
                                addItem(
                                    rv_quote_comment.adapter!!.itemCount,
                                    RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
                                )
                            }
                            imageAdapter.notifyDataSetChanged()
                        } else if (size <= 9) {
                            if (positionMain + size <= 9) {
                                if (positionMain + size == number) {
                                    for (i in mSelected.indices) {
                                        recipeList[positionMain + i].image = mSelected[i].toString()
                                        imageAdapter.notifyDataSetChanged()
                                    }
                                    addRecyclerView()
                                    addItem(
                                        rv_quote_comment.adapter!!.itemCount,
                                        RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
                                    )
                                } else if (positionMain + size <= number - 1) {
                                    for (i in mSelected.indices) {
                                        recipeList[positionMain + i].image = mSelected[i].toString()
                                        imageAdapter.notifyDataSetChanged()
                                        Log.d("here!!", positionMain.toString())
                                    }
                                } else {
                                    for (i in mSelected.indices) {
                                        recipeList[positionMain + i].image = mSelected[i].toString()
                                        addRecyclerView()

                                        Log.d("number3", number.toString())
                                        if (number > 4) {
                                            addItem(
                                                rv_quote_comment.adapter!!.itemCount,
                                                RecipeDTO.Recipe(
                                                    Integer.toString(count + 1),
                                                    "",
                                                    ""
                                                )
                                            )
                                        }

                                        imageAdapter.notifyDataSetChanged()
                                    }
                                }

                            } else if (number >= 9) {
                                Toast.makeText(
                                    App.instance,
                                    "사진을 9개 이상 전송할 수 없습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            Toast.makeText(
                                App.instance,
                                "사진을 9개 이상 전송할 수 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Log.d("number2", number.toString())
                    }
                else -> finish()
            }
        }
    }

    private fun checkPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            pickUpGallery()
        }
    }

    private fun checkThumbnailsPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permissions, PERMISSION_CODE)
        } else {
            pickUpThumbnailGallery()
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
                    imageAdapter.deleteItem(positionMain)
                    commentAdapter.deleteItem(positionMain)
                    if (positionMain == 4) {
                        addRecyclerView()
                    }
                    count--
                    number--
                    if (positionMain == 8) {
                        addRecyclerView()
                    }
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

    private fun showAddPictureDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(
            "단계별 사진이 부족해요\n" +
                    "추가 사진을 업로드 하시겠습니까?"
        )
            .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                pickUpGallery()
            })
            .setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun makeSteps() {
        steps.clear()

        for (i in commentList.indices) {
            steps.add(
                RecipeDTO.Recipe(
                    commentList[i].number,
                    saveCommentList[i].comment,
                    recipeList[i].image
                )
            )
        }

        Log.d("steps", steps.toString())
    }

    private fun checkPermissionNextButton(): Boolean {
        for (i in steps.indices) {
            Log.d("steps", steps.toString())
            if (steps[i].image == null) {
                Toast.makeText(this, "사진이 비어있습니다.", Toast.LENGTH_SHORT).show()
                return false
            }

            if (steps[i].comment!!.length < 50) {
                Toast.makeText(this, "추가 설명은 50자 이상 작성해야합니다.", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

}