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

class UploadFilterAdapter(
    val filterList: ArrayList<RecipeDTO.Themes>,
    val themes : ArrayList<RecipeDTO.Themes>,
    val saveList: ArrayList<String>
) :
    RecyclerView.Adapter<UploadFilterAdapter.UploadFilterHolder>() {
    var count = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadFilterAdapter.UploadFilterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return UploadFilterAdapter.UploadFilterHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: UploadFilterAdapter.UploadFilterHolder, position: Int) {
        holder.name.text = filterList[position].name

        holder.itemView.setOnClickListener {
            if (!saveList.contains(filterList[position].name)) {
                saveList.add(filterList[position].name.toString())
                themes.add(RecipeDTO.Themes(filterList[position].id, filterList[position].name))
                holder.name.setTextColor(Color.parseColor("#FF7051"))
                holder.name.setBackgroundResource(R.drawable.select_border_layout)
            } else {
                saveList.remove(filterList[position].name)
                themes.remove(RecipeDTO.Themes(filterList[position].id, filterList[position].name))
                holder.name.setTextColor(Color.parseColor("#777777"))
                holder.name.setBackgroundResource(R.drawable.no_select_border_layout)
            }

        }
    }

    class UploadFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}