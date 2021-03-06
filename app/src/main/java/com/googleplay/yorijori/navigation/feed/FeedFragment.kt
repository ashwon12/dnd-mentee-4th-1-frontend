/**
 *  하단 탭에서 List 선택시 보여지는 Fragment
 *
 *  게시글을 서버에서 받아서 리싸이클러뷰에 뿌려준다.
 */

package com.example.yorijori.navigation.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.App
import com.example.yorijori.R
import com.example.yorijori.SharedPreferenceUtil
import com.example.yorijori.data.datasource.remote.api.RecipeDTO
import com.example.yorijori.data.repository.Repository
import com.example.yorijori.detail.DetailFragment

class FeedFragment : Fragment(), FeedRecyclerInterface {

    private lateinit var v: View
    private var feedRecipeList =  ArrayList<RecipeDTO.RecipeFinal>()
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

        myAdapter = FeedRecyclerAdapter(this)
        rvFeed = v.findViewById(R.id.rv_list) as RecyclerView
        myAdapter.notifyDataSetChanged()

        repository.getFollowingFeeds(
            success = {
                it.run {
                    val data = it.list
                    feedRecipeList.addAll(data!!)
                    myAdapter.feedUpdateList(feedRecipeList)
                    rvFeed.adapter = myAdapter
                }
            },
            fail = {
                Log.d("fail", "failfailfail")
            },
            token = SharedPreferenceUtil(App.instance).getToken().toString()
        )
    }

    /** 게시글을 클릭했을 때 상제페이지로 이동 **/
    override fun onItemClicked(position: Int) {
        Log.d("로그", "TimeLinFragment - 클릭됨")
        Toast.makeText(
            App.instance,
            "상세 값 : ${this.feedRecipeList[position].id}",
            Toast.LENGTH_SHORT
        ).show()

        val activity = v.context as AppCompatActivity
        val detailFragment: Fragment = DetailFragment()

        val args = Bundle()// 클릭된 Recipe의 id 전달
        args.putInt("recipeId",feedRecipeList[position].id)

        detailFragment.arguments = args

        val manager: FragmentManager = activity.supportFragmentManager
        manager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .setReorderingAllowed(true)
            .replace(R.id.fl_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}