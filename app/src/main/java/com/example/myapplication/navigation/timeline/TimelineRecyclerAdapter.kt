/**
 *  각각의 item을 recyclerview로 연결 시켜주는 adapter class
 */

package com.example.myapplication.navigation.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class TimelineRecyclerAdapter(myInterface: TimelineRecyclerInterface) :
    RecyclerView.Adapter<TimelineRecyclerViewHolder>() {

    private var myInterface: TimelineRecyclerInterface? = null

    private var items = ArrayList<RecipeDTO.PostItem>()

    //생성자
    init {
        this.myInterface = myInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )
        return TimelineRecyclerViewHolder(view, this.myInterface!!)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimelineRecyclerViewHolder, position: Int) {
        holder.bind(this.items[position])
    }

    /**
     *  TimelineRecyclerAdapter 안에 보여지는 Timeline들 update
     * */
    fun updateTimelineList(timelines: List<RecipeDTO.PostItem>) {
        this.items.addAll(timelines)
    }

}