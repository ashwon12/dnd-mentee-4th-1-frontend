package com.example.myapplication.navigation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.Repository
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment() {

    private lateinit var v: View
    private var top3ImagesList = ArrayList<RecipeDTO.RecipeFinal>()
    private var popularImagesList = ArrayList<RecipeDTO.RecipeFinal>()
    private var recentImageList = ArrayList<RecipeDTO.RecipeFinal>()

    private lateinit var vp_top3: ViewPager2
    private lateinit var indicator: CircleIndicator3

    private lateinit var rv_popular: RecyclerView
    private lateinit var rv_recent : RecyclerView

    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        requestTop3Recipe()
        requestPopularRecipe()
        setRecent()

        return v
    }

    private fun requestTop3Recipe() {
        top3ImagesList.clear()
        vp_top3 = v.findViewById(R.id.vp_top3)
        vp_top3.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        indicator = v.findViewById(R.id.indicator)

        repository.getHomeRecipes(
            success = {
                it.run {
                    val data = it.list
                    for(i in 0..2){
                        top3ImagesList.add(data!!.get(i))
                    }
                    vp_top3.adapter = MultiViewAdapter(1,top3ImagesList)
                    indicator.setViewPager(vp_top3)
                }
            },
            fail = {
                Log.d("fail", "fail fail fail")
            },
            queryType = "viewTop",
            order = ""
        )
    }

    private fun requestPopularRecipe() {
        popularImagesList.clear()
        rv_popular = v.findViewById(R.id.rv_popular)
        rv_popular.layoutManager = GridLayoutManager(App.instance, 3)

        repository.getHomeRecipes(
            success = {
                it.run {
                    val data = it.list
                    popularImagesList.addAll(data!!)
                    rv_popular.adapter = MultiViewAdapter(2,popularImagesList)
                }
            },
            fail = {
                Log.d("fail", "fail fail fail")
            },
            queryType = "labelTop",
            order = ""
        )
    }

    private fun setRecent() {
        recentImageList.clear()

        rv_recent = v.findViewById(R.id.rv_recent)
        rv_recent.layoutManager = LinearLayoutManager(App.instance, RecyclerView.VERTICAL, false)

        repository.getHomeRecipes(
            success = {
                it.run {
                    val data = it.list
                    recentImageList.addAll(data!!)
                    rv_recent.adapter = MultiViewAdapter(3,recentImageList)
                }
            },
            fail = {
                Log.d("fail", "fail fail fail")
            },
            queryType = "search",
            order = "latest"
        )
    }
}

