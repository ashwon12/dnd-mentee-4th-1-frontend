package com.example.yorijori.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.R
import com.example.yorijori.data.datasource.remote.api.RecipeDTO


class DetailStepsAdapter : RecyclerView.Adapter<DetailStepsAdapter.StepsViewHolder>() {

    private lateinit var view: View

    var stepDescriptions = ArrayList<RecipeDTO.Steps>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.fragment_step_description_slide,
                parent,
                false
            )
        return StepsViewHolder(view)

    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bind(stepDescriptions[position], position)
    }

    override fun getItemCount(): Int {
        return stepDescriptions.size
    }



    inner class StepsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvStep = itemView.findViewById<TextView>(R.id.tv_step)
        private val tvStepDesc = itemView.findViewById<TextView>(R.id.tv_step_description)

        fun bind(steps: RecipeDTO.Steps, position: Int) {
            tvStep.text = "${position + 1}" + "단계"
            tvStepDesc.text = steps.description
        }
    }

    fun updateDescription(steps: ArrayList<RecipeDTO.Steps>) {
        stepDescriptions = steps
    }
}
