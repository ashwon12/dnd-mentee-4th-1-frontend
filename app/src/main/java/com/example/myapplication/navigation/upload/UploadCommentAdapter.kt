package com.example.myapplication.navigation.upload

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class UploadCommentAdapter(
    val commentList: ArrayList<RecipeDTO.Recipe>
) : RecyclerView.Adapter<UploadCommentAdapter.UploadCommentHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadCommentAdapter.UploadCommentHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upload_comment_list_item, parent, false)
        return UploadCommentHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun deleteItem(position: Int) {
        commentList.removeAt(position)
        for (i in position until commentList.size) {
            var num = Integer.parseInt(commentList[i].number)
            num--
            commentList[i].number = num.toString()
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UploadCommentAdapter.UploadCommentHolder, position: Int) {
        val element = commentList[position]
        holder.bind(element)
        holder.comment.setText(commentList[position].comment)
        holder.comment.setHint((position + 1).toString() + "번째로 해야 할 것을 적어주세요.")
        holder.comment.setHintTextColor(Color.parseColor("#C8C8C8"))
    }

    inner class UploadCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val number = itemView.findViewById<TextView>(R.id.tv_upload_number) // 단계
        var comment = itemView.findViewById<EditText>(R.id.et_upload_comment) // 레시피 설명

        fun bind(data: RecipeDTO.Recipe) {
            number.text = data.number

            comment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    commentList[adapterPosition].comment = p0.toString()
                }
            })
        }

    }

}
