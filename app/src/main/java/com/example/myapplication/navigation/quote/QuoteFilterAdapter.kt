package com.example.myapplication.navigation.quote

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

class QuoteFilterAdapter(val filterList: ArrayList<RecipeDTO.Filter>) :
    RecyclerView.Adapter<QuoteFilterAdapter.QuoteFilterHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteFilterAdapter.QuoteFilterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return QuoteFilterAdapter.QuoteFilterHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: QuoteFilterAdapter.QuoteFilterHolder, position: Int) {
        holder.name.text = filterList[position].filterName
        holder.itemView.tv_filter_name.setTextColor(Color.parseColor("#FF8C4B"))
    }

    class QuoteFilterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_filter_name)
    }
}