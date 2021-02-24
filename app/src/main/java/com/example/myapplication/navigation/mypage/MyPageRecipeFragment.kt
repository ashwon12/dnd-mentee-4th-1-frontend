package com.example.myapplication.navigation.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_mypage_recipe, container, false)

        setMyRecipe()
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
}