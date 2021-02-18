/**
 *  하단 탭에서 List 선택시 보여지는 Fragment
 *
 *  게시글을 서버에서 받아서 리싸이클러뷰에 뿌려준다.
 */

package com.example.myapplication.navigation.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository

class FeedFragment : Fragment(), TimelineRecyclerInterface {

    private lateinit var v: View

    private var list = ArrayList<RecipeDTO.PostItems>()

    private lateinit var myAdapter: TimelineRecyclerAdapter

    private val repository = Repository()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_feed, container, false)

        callRecycler()
        return v
    }

    /**
     *  fragment 생성시 리사이클러 호출해주는 함수
     */
    fun callRecycler() {
        list.clear()
        myAdapter = TimelineRecyclerAdapter(this)
        var rv_list = v.findViewById(R.id.rv_list) as RecyclerView

        /**
         * API 통신 확인을 위해 추가
         *              - 함도영
         * */
        repository.getAllTimelineList(
            success = {
                it.run {
                    Log.d("it", it.toString())
                    Log.d("it_list", listOf(it).toString())
                    Log.d("it_get(0)", it[0].toString())

                    list.add(it)

                    myAdapter.updateTimelineList(it)
                    myAdapter.notifyDataSetChanged()
                    rv_list.adapter = myAdapter
                }
            },
            fail = {
                Log.d("fail", "failfailfail")
            }
        )
    }

    override fun onItemClicked(position: Int) {
        Log.d("로그", "TimeLinFragment - 클릭됨")
        Toast.makeText(
            App.instance,
            "상세 값 : ${this.list[0][position]}",
            Toast.LENGTH_SHORT
        ).show()
    }
}