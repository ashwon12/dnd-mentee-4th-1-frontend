package com.example.yorijori.navigation.quote

import android.graphics.Color
import android.text.*
import android.text.InputFilter.LengthFilter
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.R
import com.example.yorijori.data.datasource.remote.api.RecipeDTO


class QuoteCommentAdapter(
    val commentList: ArrayList<RecipeDTO.Recipe>,
    val saveCommentList: ArrayList<RecipeDTO.Recipe>,
    val select_cut: Int
) : RecyclerView.Adapter<QuoteCommentAdapter.QuoteCommentHolder>() {
    var input: String? = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteCommentAdapter.QuoteCommentHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_comment_list_item, parent, false)
        return QuoteCommentHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun deleteItem(position: Int) {
        commentList.removeAt(position)
        saveCommentList.removeAt(position)
        Log.d("commentList del", commentList.toString())
        Log.d("savecommentList del", saveCommentList.toString())
        for (i in position until commentList.size) {
            var num = Integer.parseInt(commentList[i].number)
            num--
            commentList[i].number = num.toString()
            saveCommentList[i].number = num.toString()
        }
        Log.d("commentList del", commentList.toString())
        Log.d("savecommentList del", saveCommentList.toString())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: QuoteCommentAdapter.QuoteCommentHolder, position: Int) {
        val element = commentList[position]

        holder.bind(element)

        holder.comment.setText(commentList[position].comment)
        val wordToSpan = SpannableString(commentList[position].comment.toString())
        Log.d("wordToSpan", wordToSpan.toString())
        Log.d("input", input.toString())
        Log.d("commentList", commentList.toString())
        wordToSpan.setSpan(
            ForegroundColorSpan(Color.parseColor("#464646")),
            0,
            saveCommentList[position].comment!!.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (select_cut > position) {
            holder.comment.setText(wordToSpan)
        } else {
            holder.comment.setText(commentList[position].comment)
        }
        holder.comment.hint = (position + 1).toString() + "번째로 해야 할 것을 적어주세요."
        holder.comment.setHintTextColor(Color.parseColor("#C8C8C8"))
    }

    inner class QuoteCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var number = itemView.findViewById<TextView>(R.id.tv_upload_number) // 단계
        var comment = itemView.findViewById<EditText>(R.id.et_upload_comment) // 레시피 설명
        var len = itemView.findViewById<TextView>(R.id.tv_quote_comment_length)

        fun bind(data: RecipeDTO.Recipe) {
            number.text = data.number

            comment.setTextColor(Color.parseColor("#FF7051"))
            comment.filters =
                arrayOf<InputFilter>(LengthFilter(100 + saveCommentList[adapterPosition].comment!!.length))
            comment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    commentList[adapterPosition].comment = p0?.toString()

                    if (!p0.toString().startsWith(saveCommentList[adapterPosition].comment.toString())) {
                        comment.setText(saveCommentList[adapterPosition].comment)
                        comment.setTextColor(Color.parseColor("#464646"))
                        Selection.setSelection(comment.text, comment.length())
                    } else {
                        comment.setTextColor(Color.parseColor("#FF7051"))
                    }

                    if (select_cut > adapterPosition) {
                        if (commentList[adapterPosition].comment!!.length >= saveCommentList[adapterPosition].comment!!.length) {
                            input = commentList[adapterPosition].comment?.substring(saveCommentList[adapterPosition].comment!!.length)
                        }
                    } else {
                        input = commentList[adapterPosition].comment
                    }

                    if (input!!.length > 0) {
                        number.setBackgroundResource(R.drawable.ic_select_oval)
                    } else if (input!!.length == 0) {
                        if (commentList[adapterPosition].comment!!.length > saveCommentList[adapterPosition].comment!!.length) {
                            number.setBackgroundResource(R.drawable.ic_select_oval)
                        } else {
                            number.setBackgroundResource(R.drawable.ic_oval)
                        }
                    } else {
                        number.setBackgroundResource(R.drawable.ic_oval)
                    }
                    len.text = "글자수 : " + input?.length.toString() + "/100"
                }
            })
        }
    }
}


