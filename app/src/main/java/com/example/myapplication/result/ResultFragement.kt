package com.example.myapplication.result

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository


class ResultFragement : Fragment() {

    private lateinit var v: View

    internal lateinit var rvResults: RecyclerView
    private lateinit var resultAdapter: ResultAdapter
    private lateinit var autoTextview: AutoCompleteTextView
    private lateinit var btnSearch: Button
    private lateinit var btnClear: Button
    private lateinit var tvResultCount: TextView

    private var searchHistoryArrayList = ArrayList<String>()// 검색어 저장 List

    private var spinnerData1 = mutableListOf<String>("최신순", "별순", "조회순")
    private var spinnerData2 = mutableListOf<String>("3컷", "6컷", "9컷")
    private var spinnerData3 = mutableListOf<String>(
        "15분 이내",
        "30분 이내",
        "45분 이내",
        "60분 이내",
        "60분 이상"
    )


    private var inputTextFromSearchFragment: String? = ""

    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_result, container, false)

        setButtonSearch()
        setAutoCompleteTextView()
        setAutoCompleteTextViewClearButton()
        setResultRecyclerView()
        setSpinner()

        return v
    }

    /**  [X] 버튼 클릭 리스너  */
    private fun setAutoCompleteTextViewClearButton() {

        searchHistoryArrayList = repository.getSavedSearchList()

        val adapter = object : ArrayAdapter<String>(
            v.context,
            R.layout.custom_auto_complete_item_line,
            R.id.tv_auto_complete_item,
            searchHistoryArrayList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val btnDeleteHistory = view.findViewById<Button>(R.id.btn_delete_search_history)
                btnDeleteHistory.setOnClickListener {
                    Toast.makeText(v.context, "fafasf", Toast.LENGTH_SHORT).show()
                }
                return view
            }
        }
        autoTextview.setAdapter(adapter)

        btnClear = v.findViewById<Button>(R.id.btn_clear)
        btnClear.setOnClickListener {

            autoTextview.setText("")//Edittext 값 Null 초기화
            autoTextview.setTextColor(Color.parseColor("#000000"))

            setVisibility()
        }
    }

    private fun setAutoCompleteTextView() {
        inputTextFromSearchFragment = arguments?.getString("input_search")// arguments로 Bundle 받아옴

        autoTextview = v.findViewById<AutoCompleteTextView>(R.id.actv_recipe_in_result)
        autoTextview.setText(inputTextFromSearchFragment, TextView.BufferType.EDITABLE);
        autoTextview.setTextColor(Color.parseColor("#7A7A7A"))

        btnSearch.visibility = View.INVISIBLE// 초기 검색화면 Setting : [검색] 버튼 비활성화

        tvResultCount = v.findViewById(R.id.tv_result_count)
        tvResultCount.text = "3개"

        autoTextview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setVisibility()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }

        })
    }


    private fun setResultRecyclerView() {
        resultAdapter = ResultAdapter()
        resultAdapter.addSampleResult(
            RecipeDTO.tempResultRecipes(
                1,
                "R.drawable.ic_home",
                "간단한 햄버거 만들기",
                null,
                null,
                null,
                4.3,
                22
            )
        )
        resultAdapter.addSampleResult(
            RecipeDTO.tempResultRecipes(
                2,
                "R.drawable.ic_home",
                "간단한 햄버거 만들기",
                null,
                null,
                null,
                4.3,
                22
            )
        )
        resultAdapter.addSampleResult(
            RecipeDTO.tempResultRecipes(
                3,
                "R.drawable.ic_home",
                "간단한 햄버거 만들기",
                null,
                null,
                null,
                4.3,
                22
            )
        )
        resultAdapter.addSampleResult(
            RecipeDTO.tempResultRecipes(
                4,
                "R.drawable.ic_home",
                "간단한 햄버거 만들기",
                null,
                null,
                null,
                4.3,
                22
            )
        )

        rvResults = v.findViewById<RecyclerView>(R.id.rv_result_recipe)
        rvResults.layoutManager =
            LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        rvResults.setHasFixedSize(true)
        rvResults.adapter = resultAdapter
    }

    private fun setSpinner() {

        var adapter1 = ArrayAdapter(v.context, R.layout.spinner_item, spinnerData1)

        val spinner1 = v.findViewById<Spinner>(R.id.spinner_filter1)

        spinner1.setSelection(0)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                (view as TextView).setTextColor(Color.rgb(255, 112, 81))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("")
            }
        }
        spinner1.adapter = adapter1
    }


    private fun setButtonSearch() {
        btnSearch = v.findViewById<Button>(R.id.btn_search)
        btnSearch.setOnClickListener {

            repository.saveSearchHistory(autoTextview.text.toString()) // 검색어 저장

            val bundle = Bundle()
            bundle.putString("input_search", autoTextview.text.toString())

            val activity = v.context as AppCompatActivity
            val transaction = activity.supportFragmentManager.beginTransaction()
            val resultFragment: Fragment = ResultFragement()
            resultFragment.arguments = bundle

            transaction.replace(R.id.fl_container, resultFragment)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

    private fun setVisibility() {
        btnSearch.visibility = View.VISIBLE
        btnClear.visibility = View.INVISIBLE
        tvResultCount.visibility = View.INVISIBLE
    }
}
