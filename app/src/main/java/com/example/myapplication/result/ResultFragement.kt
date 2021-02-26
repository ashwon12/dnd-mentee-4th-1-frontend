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
import com.example.myapplication.data.repository.Repository


class ResultFragement : Fragment() {

    private lateinit var v: View

    internal lateinit var rvResults: RecyclerView
    private lateinit var autoTextview: AutoCompleteTextView
    private lateinit var btnSearch: Button
    private lateinit var btnClear: Button
    private lateinit var tvResultCount: TextView

    private lateinit var spinnerData1: MutableList<String>
    private lateinit var spinnerData2: MutableList<String>
    private lateinit var spinnerData3: MutableList<String>

    private lateinit var resultAdapter: ResultAdapter

    private var searchHistoryArrayList = ArrayList<String>()// 검색어 저장 List

    private var queryType: String = "search"
    private var stepStart: Int? = null
    private var stepEnd: Int? = null
    private var time: Int? = null
    private var startDate: String? = null
    private var endDate: String? = null
    private var order: String = "latest"
    private var keyword: String = ""
    private var limit: String? = null
    private var offset: String? = null

    private var inputTextFromSearchFragment: String? = ""

    private val repository = Repository()

    private var searchedWord: String = ""
    private var searchedCount: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_result, container, false)

        spinnerData1 = mutableListOf<String>("최신순", "별점순", "조회순", "찜순")
        spinnerData2 = mutableListOf<String>("3컷", "6컷", "9컷", "10컷+", "컷수", "컷수")
        spinnerData3 = mutableListOf<String>("15분 이내", "30분 이내", "45분 이내", "60분 이내", "60분 이상", "시간","시간")

        setButtonSearch()
        setAutoCompleteTextView()
        setResultRecyclerView()
        setSpinner()

        return v
    }

    /** 검색결과 레시피 API Data 요청
     *
     *  http://13.209.68.130:8080/recipes?queryType=search&keyword=inputTextFromSearchFragment
     **/
    private fun requestResultRecipe(keyword: String) {
        this.keyword = keyword
        repository.getResultRecipes(
            queryType,
            stepStart,
            stepEnd,
            time,
            startDate,
            endDate,
            order,
            this.keyword,
            limit,
            offset,
            success = {
                it.run {

                    if (it.list?.size!! > 0) {

                        saveCount(it.list?.size)
                        tvResultCount.text = it.list?.size!!.toString() + "개"

                    } else {// 검색결과 없을 때
                        Toast.makeText(v.context, "검색결과 개수 0개", Toast.LENGTH_SHORT).show()
                        tvResultCount.text = ""
                        val llResultItem = v.findViewById<LinearLayout>(R.id.ll_result_item_list)
                        //llResultItem.setBackgroundDrawable(R.drawable)

                    }
                    resultAdapter.updateResultRecipes(it.list!!)
                    resultAdapter.notifyDataSetChanged()
                }
            }, fail = {

            })

    }

    private fun saveCount(count: Int) {
        searchedCount = count.toString()
    }

    private fun setAutoCompleteTextView() {

        inputTextFromSearchFragment = arguments?.getString("input_search")// arguments로 Bundle 받아옴
        searchedWord = inputTextFromSearchFragment!!

        autoTextview = v.findViewById<AutoCompleteTextView>(R.id.actv_recipe_in_result)
        autoTextview.setText(searchedWord, TextView.BufferType.EDITABLE);
        autoTextview.setTextColor(Color.parseColor("#7A7A7A"))

        resultAdapter = ResultAdapter()
        resultAdapter.keyword = inputTextFromSearchFragment!!//상세레시피 -> 검색결과로 전환 시 이전 검색어 보여주기

        btnSearch.visibility = View.INVISIBLE// 초기 검색화면 Setting : [검색] 버튼 비활성화

        tvResultCount = v.findViewById(R.id.tv_result_count)
            tvResultCount.text = searchedCount + "개"

        autoTextview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setVisibility()
                autoTextview.setTextColor(Color.parseColor("#000000"))
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }

        })
    }


    private fun setResultRecyclerView() {
        rvResults = v.findViewById<RecyclerView>(R.id.rv_result_recipe)
        val llManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        rvResults.layoutManager = llManager
        rvResults.setHasFixedSize(true)
        rvResults.adapter = resultAdapter

        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
    }

    private fun setSpinner() {

        var adapter1 = ArrayAdapter(v.context, R.layout.spinner_item, spinnerData1)
        val adapter2 =
            object : ArrayAdapter<String>(v.context, R.layout.spinner_item, spinnerData2) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                    val v = super.getView(position, convertView, parent)
                    if (position == count) {
                        //마지막 포지션의 textView 를 힌트 용으로 사용합니다.
                        (v.findViewById<View>(R.id.tv_spinner_item) as TextView).text = ""
                        //아이템의 마지막 값을 불러와 hint로 추가해 줍니다.
                        (v.findViewById<View>(R.id.tv_spinner_item) as TextView).hint =
                            getItem(count)
                    }
                    return v
                }

                override fun getCount(): Int {
                    //마지막 아이템은 힌트용으로만 사용하기 때문에 getCount에 1을 빼줍니다.
                    return super.getCount() - 1
                }

            }
        val adapter3 =
            object : ArrayAdapter<String>(v.context, R.layout.spinner_item, spinnerData3) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                    val v = super.getView(position, convertView, parent)
                    if (position == count) {
                        //마지막 포지션의 textView 를 힌트 용으로 사용합니다.
                        (v.findViewById<View>(R.id.tv_spinner_item) as TextView).text = ""
                        //아이템의 마지막 값을 불러와 hint로 추가해 줍니다.
                        (v.findViewById<View>(R.id.tv_spinner_item) as TextView).hint =
                            getItem(count)
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
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        order = "latest" // 최신순
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    1 -> {
                        order = "star"// 별순
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    2 -> {
                        order = "view"// 조회순
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    3 -> {
                        order = "label"// 찜순
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                }
                (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("")
            }
        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        stepStart = 1
                        stepEnd = 3// 3컷
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    1 -> {
                        stepStart = 4
                        stepEnd = 6// 6컷
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    2 -> {
                        stepStart = 7
                        stepEnd = 9// 9컷
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    3 -> {
                        stepStart = 10
                        stepEnd = 350// 10컷 이상
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    else -> {
                        // [컷수] 선택을 위한 설정
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        time = 15 // 15분 이내
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    1 -> {
                        time = 30// 30분 이내
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    2 -> {
                        time = 45// 45분 이내
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    3 -> {
                        time = 59// 60분 이내
                        (view as? TextView)?.setTextColor(Color.rgb(255, 112, 81))
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    4 -> {
                        time = 60 // 60분 이상
                        requestResultRecipe(inputTextFromSearchFragment!!)
                    }
                    else -> {
                        //[시간] Title 설정
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("")
            }
        }


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
        btnClear = v.findViewById(R.id.btn_clear)
        btnClear.visibility = View.INVISIBLE
        btnSearch.visibility = View.VISIBLE
        tvResultCount.visibility = View.INVISIBLE
    }
}
