/**
 *  각각의 item을 recyclerview로 연결 시켜주는 adapter class
 */

package com.example.myapplication.navigation.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class TimelineRecyclerAdapter(private val items: ArrayList<RecipeDTO.Timeline>) :
    RecyclerView.Adapter<TimelineRecyclerViewHolder>() {

    /**
     *  TimelineRecyclerAdapter 안에 보여지는 Timeline들 update
     * */
    fun updateTimelineList(timelines: List<RecipeDTO.Timeline>) {
        this.items.clear()
        this.items.addAll(timelines)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )
        return TimelineRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimelineRecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

}