package com.googleplay.yorijori.navigation.upload

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.filter_list_item.view.*

class UploadPreviewFilterAdapter(val filterList: ArrayList<RecipeDTO.Themes>) :
    RecyclerView.Adapter<UploadPreviewFilterAdapter.UploadPreviewFilterHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UploadPreviewFilterAdapter.UploadPreviewFilterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return UploadPreviewFilterAdapter.UploadPreviewFilterHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: UploadPreviewFilterAdapter.UploadPreviewFilterHolder, position: Int) {
        holder.name.setText(filterList[position].name)
        holder.itemView.tv_filter_name.setBackgroundResource(R.drawable.no_select_border_layout)
        holder.itemView.tv_filter_name.setTextColor(Color.parseColor("#FF8C4B"))
    }

    class UploadPreviewFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}