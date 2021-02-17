package com.example.myapplication.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class DetailViewPagerStepsAdapter(val context: Context) : RecyclerView.Adapter<DetailViewPagerStepsAdapter.PagerViewHolder>() {

    var stepDescriptions = ArrayList<String>()//R.id.resId가 들어감

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_step_description_slide,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(stepDescriptions[position], position)
    }

    override fun getItemCount():Int = stepDescriptions.size

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvStep = itemView.findViewById<TextView>(R.id.tv_step)
        private val tvStepDesc = itemView.findViewById<TextView>(R.id.tv_step_description)

        fun bind(steDesc: String, position: Int) {
            tvStep.text = "${position+1}"+"단계"
            tvStepDesc.text = stepDescriptions[position]
        }
    }

    fun addDescription(description: String) {
        stepDescriptions.add(description)
    }
}
