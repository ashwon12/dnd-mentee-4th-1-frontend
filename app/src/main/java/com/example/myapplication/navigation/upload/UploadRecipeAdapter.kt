/**
 *  각각의 item을 recyclerview로 연결 시켜주는 adapter class
 */

package com.example.myapplication.navigation.upload

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.recipe_list_item.view.*

/**
 *  기존의 어댑터에서 '갤러리' 버튼 클릭 시 itemClick 이벤트가 생기게 추가하였습니다.
 */
class UploadRecipeAdapter(private val context: Context, private val recipeList: ArrayList<UploadRecipeDTO>, val itemClick:(Int, UploadRecipeDTO) -> Unit) :
    RecyclerView.Adapter<UploadRecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recipe_list_item,
            parent, false
        )
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val element = recipeList[position]
        holder.bind(element)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                context, recipeList.get(position).number
                        + recipeList.get(position).comment + recipeList.get(position).image + "입니다.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeNumber: TextView = itemView.tv_number
        private val recipeComment: EditText = itemView.et_comment
        private val recipePhoto: ImageView = itemView.iv_photo
        private val recipeButton: Button = itemView.btn_gallery

        fun bind(data: UploadRecipeDTO) {
            recipeNumber.text = data.number

            if(data.image != "") {
                recipePhoto.setImageURI(Uri.parse(data.image))
            } else{
                recipePhoto.setImageURI(Uri.parse(""))
            }

            recipeButton.setOnClickListener {
                itemClick(adapterPosition, data)
            }
        }
    }
}