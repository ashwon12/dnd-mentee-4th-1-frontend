/**
 *  하단 탭에서 List 선택시 보여지는 Fragment
 *
 *  게시글을 서버에서 받아서 리싸이클러뷰에 뿌려준다.
 */

package com.example.myapplication.navigation.timeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class TimelineFragment : Fragment() {

    private lateinit var v : View
  
    private var list = ArrayList<RecipeDTO.Timeline>()
  
    private lateinit var myAdapter: TimelineRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_list, container,false)

        callRecycler()
        return v
    }

    /**
     *  fragment 생성시 리사이클러 호출해주는 함수
     */
    fun callRecycler(){
        list.clear()
        list.add(RecipeDTO.Timeline("1","여기는 제목이에요~~", "여기는 subTitle이에요~~~"))
        list.add(RecipeDTO.Timeline("1","여기는 제목이에요~~", "여기는 subTitle이에요~~~"))
        list.add(RecipeDTO.Timeline("1","여기는 제목이에요~~", "여기는 subTitle이에요~~~"))

        myAdapter = TimelineRecyclerAdapter(list)
        myAdapter.notifyDataSetChanged()

        var rv_list = v.findViewById(R.id.rv_list) as RecyclerView
        rv_list.adapter = myAdapter
    }

}