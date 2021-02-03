package com.example.myapplication.navigation.upload

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.filter_list_item.view.*

class UploadFilterAdpater(val filterList: ArrayList<RecipeDTO.Filter>) :
    RecyclerView.Adapter<UploadFilterAdpater.UploadFilterHolder>() {

    var savefilter = ArrayList<RecipeDTO.Filter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UploadFilterAdpater.UploadFilterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return UploadFilterAdpater.UploadFilterHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: UploadFilterAdpater.UploadFilterHolder, position: Int) {
        holder.name.setText(filterList[position].filterName)

        holder.itemView.setOnClickListener {
            if (!savefilter.contains(RecipeDTO.Filter(filterList[position].filterName))) {
                savefilter.add(RecipeDTO.Filter(filterList[position].filterName))
                holder.itemView.tv_filter_name.setBackgroundResource(R.drawable.select_border_layout)
            } else {
                savefilter.remove(RecipeDTO.Filter(filterList[position].filterName))
                holder.itemView.tv_filter_name.setBackgroundResource(R.drawable.no_select_border_layout)
            }

            Log.d("filter", savefilter.toString())
        }
    }

    class UploadFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}