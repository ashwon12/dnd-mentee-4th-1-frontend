package com.example.myapplication.navigation.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
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
        // 자동완성기능 Sample Data들
        searchHistoryArrayList.add(0, "aaa")
        searchHistoryArrayList.add(0, "adsvabbd")
        searchHistoryArrayList.add(0, "acccccccccc")
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

        // 키보드 입력 후 [Enter]클릭 리스너
        autoTextView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // TODO : 목록에 없는 Text 검색 시, Data목록 사라지는 기능
                hideKeyboard(autoTextView)
            }
            true
        })

        // 자동완성목록 Item 클릭 리스너
        autoTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                hideKeyboard(autoTextView)
                Toast.makeText(v.context, "Selected : $selectedItem", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setRecyclerView() {
        searchAdapter = SearchRecyclerAdapter()
        val rvRandomRecipe = v.findViewById<RecyclerView>(R.id.rv_random_recipe)
        rvRandomRecipe.isNestedScrollingEnabled//주의
        rvRandomRecipe.adapter = searchAdapter
        rvRandomRecipe.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        requestAllTimelines()
    }

    /** 스와이프 동작 시, 리싸이클러뷰 아이템 재요청 */
    private fun setSwipeRefreshLayout() {
        v.findViewById<SwipeRefreshLayout>(R.id.srl_update).setOnRefreshListener {
            Toast.makeText(v.context, "목록들 가져오는중", Toast.LENGTH_SHORT).show()
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

    /** 키보드 숨기기 */
    private fun hideKeyboard(view: AutoCompleteTextView) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}