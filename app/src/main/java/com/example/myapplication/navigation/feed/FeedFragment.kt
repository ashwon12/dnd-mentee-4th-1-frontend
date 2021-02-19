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

class FeedFragment : Fragment(), FeedRecyclerInterface {

    private lateinit var v: View
    private var feedRecipeList =  ArrayList<RecipeDTO.tempRandomRecipes>()
    private lateinit var myAdapter: FeedRecyclerAdapter
    private val repository = Repository()
    private lateinit var rvFeed : RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_feed, container, false)

        requsetRecipie()
        return v
    }

    private fun requsetRecipie() {
        feedRecipeList.clear()

        feedRecipeList.add(RecipeDTO.tempRandomRecipes(0,"https://t1.daumcdn.net/cfile/tistory/9954B44D5B0AB2E22C","소고기",null,null,null,4.1,null))
        feedRecipeList.add(RecipeDTO.tempRandomRecipes(1,"https://t1.daumcdn.net/cfile/tistory/9954B44D5B0AB2E22C","햄버거",null,null,null,4.0,null))
        feedRecipeList.add(RecipeDTO.tempRandomRecipes(2,"https://t1.daumcdn.net/cfile/tistory/9954B44D5B0AB2E22C","감자튀김",null,null,null,4.0,null))
        feedRecipeList.add(RecipeDTO.tempRandomRecipes(3,"https://t1.daumcdn.net/cfile/tistory/9954B44D5B0AB2E22C","레드벨벳 케이크",null,null,null,4.0,null))
        feedRecipeList.add(RecipeDTO.tempRandomRecipes(4,"https://t1.daumcdn.net/cfile/tistory/9954B44D5B0AB2E22C","버팔로윙",null,null,null,4.0,null))

        myAdapter = FeedRecyclerAdapter(this)
        rvFeed = v.findViewById(R.id.rv_list) as RecyclerView

        repository.getRandomRecipes(
            success = {
                it.run {
                    feedRecipeList.add(it)
                    myAdapter.feedUpdateList(feedRecipeList)
                    myAdapter.notifyDataSetChanged()
                    rvFeed.adapter = myAdapter
                }
            },
            fail = {
                Log.d("fail", "failfailfail")
            }
        )
        myAdapter.feedUpdateList(feedRecipeList)
        myAdapter.notifyDataSetChanged()
        rvFeed.adapter = myAdapter
    }


    /** 게시글을 클릭했을 때 상제페이지로 이동 **/
    override fun onItemClicked(position: Int) {
        Log.d("로그", "TimeLinFragment - 클릭됨")
        Toast.makeText(
            App.instance,
            "상세 값 : ${this.feedRecipeList[position]}",
            Toast.LENGTH_SHORT
        ).show()
    }
}