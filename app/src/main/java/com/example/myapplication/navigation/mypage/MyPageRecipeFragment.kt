package com.example.myapplication.navigation.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.navigation.home.HomeMultiViewAdapter
import kotlinx.android.synthetic.main.fragment_mypage_recipe.*

class MyPageRecipeFragment : Fragment() {

    private lateinit var v: View
    private var myRecipeList = ArrayList<RecipeDTO.RecipeFinal>()
    private lateinit var rv_my_recipe : RecyclerView

    private val repository = Repository()

    private var spinnerData1 = mutableListOf<String>("최신순", "별순", "조회순")
    private var spinnerData2 = mutableListOf<String>("3컷", "6컷", "9컷","컷수")
    private var spinnerData3 = mutableListOf<String>(
        "15분 이내",
        "30분 이내",
        "45분 이내",
        "60분 이내",
        "60분 이상",
        "시간"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_mypage_recipe, container, false)

        setMyRecipe()
        setSpinner()

        return v
    }

    private fun setMyRecipe() {
        myRecipeList.clear()
        rv_my_recipe = v.findViewById(R.id.rv_my_recipe)
        rv_my_recipe.layoutManager = GridLayoutManager(App.instance, 2)

        //TODO: 로그인 토큰으로 api 변경해야함
        repository.getHomeRecipes(
            success = {
                it.run {
                    val data = it.list
                    myRecipeList.addAll(data!!)
                    rv_my_recipe.adapter = MyMultiViewAdapter(1,myRecipeList)
                    tv_my_recipie_count.text = "전체 ${data.size}개"
                }
            },
            fail = {
                Log.d("fail", "fail fail fail")
            },
            queryType = "viewTop",
            order = ""
        )
    }

    private fun setSpinner() {

        var adapter1 = ArrayAdapter(v.context, R.layout.mypage_spinner_item, spinnerData1)
        val adapter2 = object : ArrayAdapter<String>(v.context, R.layout.mypage_spinner_item, spinnerData2) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    //마지막 포지션의 textView 를 힌트 용으로 사용합니다.
                    (v.findViewById<View>(R.id.tv_mypage_spinner_item) as TextView).text = ""
                    //아이템의 마지막 값을 불러와 hint로 추가해 줍니다.
                    (v.findViewById<View>(R.id.tv_mypage_spinner_item) as TextView).hint = getItem(count)
                }
                return v
            }

            override fun getCount(): Int {
                //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줍니다.
                return super.getCount() - 1
            }

        }
        val adapter3 = object : ArrayAdapter<String>(v.context, R.layout.mypage_spinner_item, spinnerData3) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    //마지막 포지션의 textView 를 힌트 용으로 사용합니다.
                    (v.findViewById<View>(R.id.tv_mypage_spinner_item) as TextView).text = ""
                    //아이템의 마지막 값을 불러와 hint로 추가해 줍니다.
                    (v.findViewById<View>(R.id.tv_mypage_spinner_item) as TextView).hint = getItem(count)
                }
                return v
            }

            override fun getCount(): Int {
                //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줍니다.
                return super.getCount() - 1
            }

        }

        val spinner1 = v.findViewById<Spinner>(R.id.spinner_filter1)
        val spinner2 = v.findViewById<Spinner>(R.id.spinner_filter2)
        val spinner3 = v.findViewById<Spinner>(R.id.spinner_filter3)

        spinner1.adapter = adapter1
        spinner2.adapter = adapter2
        spinner3.adapter = adapter3

        spinner1.setSelection(0)// "최신순" 선택
        spinner2.setSelection(adapter2.count)// "컷수" 제목 설정
        spinner3.setSelection(adapter3.count)// "시간" 제목 설정

        // Spinner 클릭 리스너
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("")
            }
        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("")
            }
        }
        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("")
            }
        }


    }
}