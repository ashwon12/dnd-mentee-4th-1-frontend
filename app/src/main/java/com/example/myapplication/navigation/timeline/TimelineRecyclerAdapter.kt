/**
 *  각각의 item을 recyclerview로 연결 시켜주는 adapter class
 */

package com.example.myapplication.navigation.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class TimelineRecyclerAdapter(private val items: ArrayList<TimelineDTO>) :
    RecyclerView.Adapter<TimelineRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false)
        return TimelineRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TimelineRecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

}