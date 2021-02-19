package com.example.myapplication.navigation.upload

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_upload2.*
import kotlinx.android.synthetic.main.activity_upload3.*
import kotlinx.android.synthetic.main.activity_upload3.iv_upload_cancel

class UploadActivity3 : AppCompatActivity() {

    companion object {
        private const val REQUEST_GET_IMAGE = 105
        private const val PERMISSION_CODE = 100
    }

    private var number = 1
    private var count = 0
    private var recipeTitle: String? = null
    private var recipeList = ArrayList<RecipeDTO.Recipe>()
    private var commentList = ArrayList<RecipeDTO.Recipe>()
    private var saveFilterList = ArrayList<String>()
    private var filterList = ArrayList<RecipeDTO.Filter>()
    private var thumbnail: Uri? = null
    private var mainFoodTagList = ArrayList<String>()
    private var subFoodTagList = ArrayList<String>()
    private var positionMain = 0
    private var steps = ArrayList<RecipeDTO.Recipe>()

    private lateinit var adapter: UploadRecipeAdapter
    private lateinit var commentAdapter: UploadCommentAdapter
    private lateinit var itemMain: RecipeDTO.Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload3)

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
    }

    private fun addButtonClick() {
        if (count < 9) {
            Log.d("click", "클릭됨")
            Log.d("commentList", commentList.toString())
            addItem(
                rv_upload_comment.adapter!!.itemCount,
                RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
            )
            addRecyclerView()
            adapter.notifyDataSetChanged()

        } else {
            Toast.makeText(this, "9개 이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun itemTouch() {
        val item = object : UploadSwapDelete(this, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                commentAdapter.deleteItem(viewHolder.adapterPosition)
                Log.d("id", viewHolder.itemId.toString())
                Log.d("position", viewHolder.adapterPosition.toString())
                Log.d("direction", direction.toString())
                Log.d("positionMain", positionMain.toString())
                adapter.deleteItem(viewHolder.layoutPosition)
                number--
                count--
            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(rv_upload_comment)
    }

    private fun addItem(position: Int, data: RecipeDTO.Recipe) {
        if (count < 9) {
            commentList.add(position, data)
            commentAdapter.notifyDataSetChanged()
            count++
        }
    }

    private fun clickPrevButton() {
        val intent = Intent(this, UploadActivity2::class.java)
        intent.putExtra("number", number)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("originFilter", filterList)
        intent.putExtra("title", recipeTitle)
        intent.putExtra("steps", steps)
        startActivity(intent)
        finish()
    }

    private fun clickPreviewButton() {
        makeSteps()
        val intent = Intent(App.instance, UploadActivity4::class.java)
        intent.putExtra("recipeTitle", recipeTitle)
        intent.putExtra("originFilger", filterList)
        intent.putExtra("filter", saveFilterList)
        intent.putExtra("steps", steps)
        intent.putExtra("mainfood", mainFoodTagList)
        intent.putExtra("subfood", subFoodTagList)
        intent.putExtra("thumbnail", thumbnail)
        intent.putExtra("number", count)

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

    private fun makeRecyclerView() {
        recipeList.clear()
        recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
        // number++
    }

    private fun addRecyclerView() {
        if (number < 9) {
            number++
            recipeList.add(RecipeDTO.Recipe(number.toString(), null, null))
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
                        Log.d("positionMain", positionMain.toString())
                        Log.d("size", size.toString())
                        Log.d("number1", number.toString())
                        if (size == 1) {
                            recipeList[positionMain].image = mSelected[0].toString()
                            addRecyclerView()
                            if (positionMain >= number) {
                                addItem(
                                    rv_upload_comment.adapter!!.itemCount,
                                    RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
                                )
                            }
                            adapter.notifyDataSetChanged()
                        } else if (size <= 9) {
                            if (positionMain + size <= 9) {
                                if (positionMain + size <= number) {
                                    for (i in mSelected.indices) {
                                        recipeList[positionMain + i].image = mSelected[i].toString()
//                                        addItem(
//                                            rv_upload_comment.adapter!!.itemCount,
//                                            RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
//                                        )
                                        adapter.notifyDataSetChanged()
                                    }
                                    // addRecyclerView()
                                } else {
                                    for (i in mSelected.indices) {
                                        recipeList[positionMain + i].image = mSelected[i].toString()
                                        addRecyclerView()

                                        Log.d("number3", number.toString())
                                        addItem(
                                            rv_upload_comment.adapter!!.itemCount,
                                            RecipeDTO.Recipe(Integer.toString(count + 1), "", "")
                                        )
                                        adapter.notifyDataSetChanged()
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

    private fun pickUpGallery() {
        Matisse.from(this)
            .choose(MimeType.ofAll())
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
                if (positionMain < count || positionMain == 8) {
                    adapter.deleteItem(positionMain)
                    commentAdapter.deleteItem(positionMain)
                    count--
                    number--
                    if (positionMain == 8) {
                        addRecyclerView()
                    }
                } else {
                    Toast.makeText(this, "삭제하실 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                Log.d("dialog_number", number.toString())
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    private fun makeSteps() {
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
}