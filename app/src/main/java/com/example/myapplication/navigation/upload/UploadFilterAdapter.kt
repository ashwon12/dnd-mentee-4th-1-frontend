package com.example.myapplication.navigation.upload

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import kotlinx.android.synthetic.main.filter_list_item.view.*

class UploadFilterAdapter(
    val filterList: ArrayList<RecipeDTO.Filter>,
    val saveList: ArrayList<String>
) :
    RecyclerView.Adapter<UploadFilterAdapter.UploadFilterHolder>() {

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
        holder.name.text = filterList[position].filterName

        holder.itemView.setOnClickListener {
            if (!saveList.contains(filterList[position].filterName)) {
                saveList.add(filterList[position].filterName)
                holder.name.setTextColor(Color.parseColor("#FF7051"))
            } else {
                saveList.remove(filterList[position].filterName)
                holder.name.setTextColor(Color.parseColor("#777777"))
            }
        }
    }

    class UploadFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}