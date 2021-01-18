/**
 *  하단 탭에서 Write 선택시 보여지는 Fragment
 *
 *  레시피를 입력받아서 서버로 전송해준다.
 */

package com.example.myapplication.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.RecipeAdapter
import com.example.myapplication.RecipeData
import kotlinx.android.synthetic.main.fragment_write.*
import java.lang.Exception

class WriteFragment : Fragment() {

    private var count = 1
    private val REQUEST_GET_IMAGE = 105
    private var positionMain = 0
    private var list =  ArrayList<RecipeData>()
    private lateinit var v: View
    private lateinit var adapter: RecipeAdapter
    private lateinit var itemMain: RecipeData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_write, container, false)
        var btn_recipe_add = v.findViewById(R.id.btn_recipe_add) as Button
        var btn_recipe_del = v.findViewById(R.id.btn_recipe_del) as Button
        var btn_submit = v.findViewById(R.id.btn_submit) as Button

        list.add(RecipeData("1번", null, ""))

        callRecycler()

        /**
         *  '요리 순서 추가' 버튼 이벤트
         */
        btn_recipe_add.setOnClickListener {
            list.add(RecipeData(Integer.toString(count + 1) + "번", null, ""))
            count++;
            adapter.notifyDataSetChanged()
        }

        /**
         *  '요리 순서 삭제' 버튼 이벤트
         */
        btn_recipe_del.setOnClickListener {
            list.removeAt(count - 1)
            count--;
            adapter.notifyDataSetChanged()
        }

        /**
         *  '전송' 버튼 이벤트
         */
        btn_submit.setOnClickListener { }

        return v
    }

    /**
     *  갤러리에서 사진을 눌렀을 때 요청 처리하는 함수.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GET_IMAGE ->
                    try {
                        var uri = data?.data
                        list[positionMain].image = uri.toString()
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     *  리사이클러 뷰 생성 및 갤러리 버튼 클릭 시 갤러리 호출하는 함수
     */
    fun callRecycler(){
        var rv_recipe_list = v.findViewById(R.id.rv_recipe_list) as RecyclerView

        adapter = RecipeAdapter(v.context, list) { position, item ->
            positionMain = position
            itemMain = item

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_GET_IMAGE)
        }

        rv_recipe_list.adapter = adapter
        rv_recipe_list.layoutManager = LinearLayoutManager(v.context)

    }
}