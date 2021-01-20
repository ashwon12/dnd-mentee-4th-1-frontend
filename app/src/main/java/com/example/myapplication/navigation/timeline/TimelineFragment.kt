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
import com.example.myapplication.data.repository.RepositoryImpl

class TimelineFragment : Fragment() {

    private lateinit var v : View
  
    private var list = ArrayList<RecipeDTO.Timeline>()
  
    private lateinit var myAdapter: TimelineRecyclerAdapter

    private val repository = RepositoryImpl()

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
        list.add(RecipeDTO.Timeline("1","여기는 제목이에요~~", "여기는 subTitle이에요~~~",
            listOf("https://image-notepet.akamaized.net/resize/620x-/seimage/20191114%2F6a4c967c5b14197dd5d2c47424ae8e82.jpg")
        ))
/*        list.add(RecipeDTO.Timeline("1","여기는 제목이에요~~", "여기는 subTitle이에요~~~"))
        list.add(RecipeDTO.Timeline("1","여기는 제목이에요~~", "여기는 subTitle이에요~~~"))*/
        /**
         * API 통신 확인을 위해 추가
         *              - 함도영
         * */
        repository.getAllTimelineList(
            success = {
                it.items.run {
                    list.addAll(it.items)
                }
            },
            fail = {

            }
        )
        myAdapter = TimelineRecyclerAdapter(list)
        myAdapter.notifyDataSetChanged()

        var rv_list = v.findViewById(R.id.rv_list) as RecyclerView
        rv_list.adapter = myAdapter
    }

}