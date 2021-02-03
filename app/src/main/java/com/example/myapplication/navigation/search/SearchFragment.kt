package com.example.myapplication.navigation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.RepositoryImpl
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var v: View
    private lateinit var searchAdapter: SearchRecyclerAdapter

    private var tempList = ArrayList<RecipeDTO.PostItems>()

    val searchHistoryArrayList = ArrayList<String>()// 검색어 저장 List

    private val repository = RepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_search, container, false)



        setAutoCompleteTextView()
        setRecyclerView()
        setSwipeRefreshLayout()

        return v
    }

    private fun setAutoCompleteTextView() {
        searchHistoryArrayList.add(0, "aaa")
        searchHistoryArrayList.add(0, "bbb")
        searchHistoryArrayList.add(0, "ccc")
        searchHistoryArrayList.add(0, "ddd")
        searchHistoryArrayList.add(0, "eee")
        val autoTextView = v.findViewById<AutoCompleteTextView>(R.id.actv_recipe)
        val adapter = ArrayAdapter<String>(
            v.context,
            android.R.layout.simple_dropdown_item_1line,
            searchHistoryArrayList
        )
        autoTextView.setAdapter(adapter)
    }

    private fun setRecyclerView() {
        searchAdapter = SearchRecyclerAdapter()
        var rvRandomRecipe = v.findViewById<RecyclerView>(R.id.rv_random_recipe)
        rvRandomRecipe.isNestedScrollingEnabled//주의
        rvRandomRecipe.adapter = searchAdapter
        rvRandomRecipe.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        requestAllTimelines()
    }

    private fun setSwipeRefreshLayout() {
        v.findViewById<SwipeRefreshLayout>(R.id.srl_update).setOnRefreshListener {
            requestAllTimelines()           //repository에게 재요청
            srl_update.isRefreshing = false //swipe 에니메이션 삭제
        }
    }

    /** Repository에게 타임라인 목록 요청 */
    private fun requestAllTimelines() {
        searchAdapter.randomRecipes.clear()
        repository.getAllTimelineList(
            success = {
                it.run {

                    tempList.add(it)

                    searchAdapter.updateRandomRecipeList(it)
                    searchAdapter.notifyDataSetChanged()
                }
            },
            fail = {
                Log.d("fail", "failfailfail")
            }
        )
    }
}