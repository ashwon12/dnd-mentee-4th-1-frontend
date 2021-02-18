package com.example.myapplication.navigation.upload

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class UploadRecipeAdapter(
    val recipeList: ArrayList<RecipeDTO.Recipe>,
    val itemClick: (Int, RecipeDTO.Recipe) -> Unit
) : RecyclerView.Adapter<UploadRecipeAdapter.UploadRecipeHolder>() {
    var onItemLongClick: ((Int, RecipeDTO.Recipe) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadRecipeAdapter.UploadRecipeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upload_recipe_list_item, parent, false)
        return UploadRecipeHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: UploadRecipeAdapter.UploadRecipeHolder, position: Int) {
        val element = recipeList[position]
        holder.bind(element)
    }

    fun deleteItem(position: Int) {
        recipeList.removeAt(position)
        for (i in position until recipeList.size) {
            var num = Integer.parseInt(recipeList[i].number)
            num--
            recipeList[i].number = num.toString()
        }
        notifyDataSetChanged()
    }

    inner class UploadRecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val number = itemView.findViewById<TextView>(R.id.tv_number) // 단계
        private val image = itemView.findViewById<ImageView>(R.id.iv_photo) // 레시피 사진

        fun bind(data: RecipeDTO.Recipe) {
            number.text = data.number

            if (data.image != null) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(image)
            } else {
                Glide.with(itemView.context)
                    .load("")
                    .into(image)
            }

            image.setOnClickListener {
                itemClick(adapterPosition, data)
            }

            image.setOnLongClickListener {
                onItemLongClick?.invoke(adapterPosition, data)

                true
            }
        }
    }
}
