package com.example.myapplication.navigation.quote

import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class QuoteCommentAdapter(
    val commentList: ArrayList<RecipeDTO.Recipe>,
    val select_cut: Int
) : RecyclerView.Adapter<QuoteCommentAdapter.QuoteCommentHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteCommentAdapter.QuoteCommentHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upload_comment_list_item, parent, false)
        return QuoteCommentHolder(view)
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

    override fun onBindViewHolder(holder: QuoteCommentAdapter.QuoteCommentHolder, position: Int) {
        val element = commentList[position]
        holder.bind(element)
        holder.comment.setText(commentList[position].comment)
        holder.comment.setHint((position + 1).toString() + "번째로 해야 할 것을 적어주세요.")
        holder.comment.setHintTextColor(Color.parseColor("#C8C8C8"))
    }

    inner class QuoteCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var number = itemView.findViewById<TextView>(R.id.tv_upload_number) // 단계
        var comment = itemView.findViewById<EditText>(R.id.et_upload_comment) // 레시피 설명

        fun bind(data: RecipeDTO.Recipe) {
            number.text = data.number
            val saveComment = commentList[adapterPosition].comment
            val wordToSpan = SpannableString(saveComment)
            var count = true
            wordToSpan.setSpan(ForegroundColorSpan(Color.parseColor("#464646")) ,0,wordToSpan.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            comment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if(!p0.toString().startsWith(saveComment.toString()) || count == true) {
                        count = false
                        comment!!.setText(wordToSpan)
                        Selection.setSelection(comment.text,comment.length())
                    } else {
                        commentList[adapterPosition].comment = p0.toString()
                        comment.setTextColor(Color.parseColor("#FF7051"))
                    }

                    Log.d("p0", p0.toString())
                }
            })
        }
    }

}
