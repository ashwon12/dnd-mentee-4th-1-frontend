package com.example.myapplication.navigation.upload

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
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

class UploadRecipeAdapter(
    val recipeList: ArrayList<RecipeDTO.Recipe>,
    val itemClick: (Int, RecipeDTO.Recipe) -> Unit
) : RecyclerView.Adapter<UploadRecipeAdapter.UploadRecipeHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadRecipeAdapter.UploadRecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upload_recipe_list_item, parent, false)
        return UploadRecipeHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: UploadRecipeAdapter.UploadRecipeHolder, position: Int) {
        val element = recipeList[position]
        holder.bind(element)
        // holder.comment.setText(recipeList[position].comment)

//        holder.itemView.setOnClickListener {
//            Toast.makeText(
//                App.instance,
//                "번호 : " + recipeList.get(position).number
//                        + "내용 : " + recipeList.get(position).comment + "사진 : " + recipeList.get(position).image + "입니다.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }


    inner class UploadRecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val number = itemView.findViewById<TextView>(R.id.tv_number) // 단계
        private val image = itemView.findViewById<ImageView>(R.id.iv_photo) // 레시피 사진
        // var comment = itemView.findViewById<EditText>(R.id.et_recipe_explain) // 레시피 설명

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

//            comment.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//
//                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//
//                override fun afterTextChanged(p0: Editable?) {
//                    recipeList[adapterPosition].comment = p0.toString()
//                }
//            })
        }

    }

}
