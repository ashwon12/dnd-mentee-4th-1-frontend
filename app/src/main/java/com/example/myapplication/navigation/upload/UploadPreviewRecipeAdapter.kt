package com.example.myapplication.navigation.upload

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import java.io.Serializable

class UploadPreviewRecipeAdapter(
    val recipeList: ArrayList<RecipeDTO.Recipe>
) : RecyclerView.Adapter<UploadPreviewRecipeAdapter.UploadPreviewRecipeHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadPreviewRecipeAdapter.UploadPreviewRecipeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upload_preview_recipe_list_item, parent, false)
        return UploadPreviewRecipeHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: UploadPreviewRecipeAdapter.UploadPreviewRecipeHolder, position: Int) {
        val element = recipeList[position]
        holder.bind(element)
    }

    inner class UploadPreviewRecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val number = itemView.findViewById<TextView>(R.id.tv_number2) // 단계
        private val image = itemView.findViewById<ImageView>(R.id.iv_photo2) // 레시피 사진
        private val comment = itemView.findViewById<TextView>(R.id.tv_recipe_explain2) // 레시피 설명

        fun bind(data: RecipeDTO.Recipe) {
            number.text = data.number + "번째"
            comment.text = data.comment

            if (data.image != null) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(image)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.gallery)
                    .into(image)
            }
        }
    }
}
