package com.example.myapplication.navigation.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), View.OnClickListener {

    private lateinit var v: View

    private lateinit var searchAdapter: SearchAdapter

    private val tempList = ArrayList<RecipeDTO.PostItems>()

    private var searchHistoryArrayList = ArrayList<String>()// 검색어 저장 List

    private val repository = Repository()

    private lateinit var tvRecommand: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_search, container, false)
        setRecyclerView()
        setAutoCompleteTextView()

        pickRandomNumberOnRecommandTextView()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setSwipeRefreshLayout()
    }

    /**   AutoCompleteTextView 설정   */
    private fun setAutoCompleteTextView() {
        // 자동완성기능 Sample Data들
        searchAdapter.notifyDataSetChanged()
        searchHistoryArrayList = repository.getSavedSearchList()

        val autoTextView = v.findViewById<AutoCompleteTextView>(R.id.actv_recipe)
        val adapter = ArrayAdapter<String>(
            v.context,
            R.layout.custom_auto_complete_item_line,
            R.id.tv_auto_complete_item,
            searchHistoryArrayList
        )
        autoTextView.setAdapter(adapter)

        // 키보드 입력 후 [Enter]클릭 리스너
        autoTextView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(autoTextView)
                Toast.makeText(v.context, "그냥 엔터로 검색", Toast.LENGTH_SHORT).show()

                // TODO : 목록에 없는 Text 검색 시, Data목록 사라지는 기능
                //v.visibility = View.GONE

                // 검색어 저장
                repository.saveSearch(v.text.toString())
            }
            searchAdapter.notifyDataSetChanged()
            adapter.notifyDataSetChanged()//TODO : 방금 검색한 Text 바로 검색기록에 안뜸
            true
        })

        // 자동완성목록 Item 클릭 리스너
        autoTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                hideKeyboard(autoTextView)
                Toast.makeText(v.context, "Selected : $selectedItem", Toast.LENGTH_SHORT).show()
                // 검색어 저장
                repository.saveSearch(selectedItem)
            }
    }

    /**   RecyclerView 설정   */
    private fun setRecyclerView() {
        searchAdapter = SearchAdapter()

        val sgLayoutManager = SpannedGridLayoutManager(
            orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
            spans = 6
        )
        sgLayoutManager.itemOrderIsStable = true

        val rvRandomRecipe = v.findViewById<RecyclerView>(R.id.rv_random_recipe)
        rvRandomRecipe.layoutManager = sgLayoutManager
        rvRandomRecipe.adapter = searchAdapter
        rvRandomRecipe.isNestedScrollingEnabled//주의

        sgLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            when (position % 9) {
                0, 6 -> {
                    SpanSize(4, 4)
                }
                3 -> {
                    SpanSize(3, 3)
                }
                else -> {
                    SpanSize(2, 2)
                }
            }
        }
        requestRandomRecipes()
    }

    /**  스와이프 동작 시, 리싸이클러뷰 아이템 재요청  */
    private fun setSwipeRefreshLayout() {
        val srl_update = v.findViewById<SwipeRefreshLayout>(R.id.srl_update)
        srl_update.setColorSchemeResources(R.color.colorAccent)
        srl_update.setOnRefreshListener {
            Toast.makeText(v.context, "목록들 가져오는중", Toast.LENGTH_SHORT).show()
            requestRandomRecipes()           //repository에게 재요청
            srl_update.isRefreshing = false //swipe 에니메이션 삭제

            pickRandomNumberOnRecommandTextView()
        }
    }

    /**  Repository에게 타임라인 목록 요청  */
    private fun requestRandomRecipes() {
        searchAdapter.randomRecipes.clear()
        repository.getAllTimelineList(//TODO : getAllTimelinesList -> getRandomRecipes
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

    /** Random 뽑기 in (3, 6, 9) */
    private fun pickRandomNumberOnRecommandTextView() {
        val arr369 = arrayOf(3, 6, 9)
        val randomText = arr369.get(Random().nextInt(3))

        tvRecommand = v.findViewById<TextView>(R.id.tv_recommand)
        tvRecommand.text = "오늘은 $randomText" + "컷 요리 어때요?"
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_delete_search_history -> {
                Toast.makeText(v.context, "onCick test", Toast.LENGTH_SHORT).show()
            }
        }
    }
}