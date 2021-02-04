package com.example.myapplication.navigation.upload

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.filter_list_item.view.*

class UploadPreviewFilterAdapter(val filterList: ArrayList<RecipeDTO.Filter>) :
    RecyclerView.Adapter<UploadPreviewFilterAdapter.UploadPreviewFilterHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UploadPreviewFilterAdapter.UploadPreviewFilterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return UploadPreviewFilterAdapter.UploadPreviewFilterHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: UploadPreviewFilterAdapter.UploadPreviewFilterHolder, position: Int) {
        holder.name.setText(filterList[position].filterName)
        holder.itemView.tv_filter_name.setBackgroundResource(R.drawable.select_border_layout)
    }

    class UploadPreviewFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}