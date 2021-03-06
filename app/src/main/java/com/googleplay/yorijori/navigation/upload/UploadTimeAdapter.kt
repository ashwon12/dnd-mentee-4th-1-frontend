package com.googleplay.yorijori.navigation.upload

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO

class UploadTimeAdapter(
    val timeList: ArrayList<RecipeDTO.Time>,
    var saveTime: String?,
    val itemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<UploadTimeAdapter.UploadTimeHolder>() {

    private var currentPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadTimeAdapter.UploadTimeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.time_list_item, parent, false)
        return UploadTimeAdapter.UploadTimeHolder(view)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: UploadTimeAdapter.UploadTimeHolder, position: Int) {
        holder.name.text = timeList[position].timeName

        holder.itemView.setOnClickListener {
            currentPosition = position
            saveTime = timeList[position].timeName
           // Log.d("savetime", saveTime + " savetime")
            notifyDataSetChanged()
        }

        if (currentPosition == position) {
            itemClick(currentPosition)

            saveTime = timeList[position].timeName
            // Log.d("savetime", saveTime + " savetime")
            holder.name.setTextColor(Color.parseColor("#FF7051"))
            holder.name.setBackgroundResource(R.drawable.select_border_layout)
        } else {
            holder.name.setTextColor(Color.parseColor("#8E8E93"))
            holder.name.setBackgroundResource(R.drawable.no_select_border_layout)
        }
    }

    class UploadTimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_time_name)
    }
}