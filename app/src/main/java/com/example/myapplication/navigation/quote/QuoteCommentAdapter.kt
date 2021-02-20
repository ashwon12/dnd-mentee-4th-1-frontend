package com.example.myapplication.navigation.quote

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
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO


class QuoteCommentAdapter(
    val commentList: ArrayList<RecipeDTO.Recipe>,
    val saveCommentList: ArrayList<RecipeDTO.Recipe>,
    val select_cut: Int
) : RecyclerView.Adapter<QuoteCommentAdapter.QuoteCommentHolder>() {
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
        var len = itemView.findViewById<TextView>(R.id.tv_quote_comment_length)

        fun bind(data: RecipeDTO.Recipe) {
            number.text = data.number

            // var saveComment = commentList[adapterPosition].comment
            var saveComment = commentList[adapterPosition].comment
            var input = commentList[adapterPosition].comment?.substring(saveComment!!.length)
            var wordToSpan = SpannableString(saveComment)
            Log.d("wordToSpan", wordToSpan.toString())

            Log.d("input", input.toString())
            Log.d("commentList", commentList.toString())
            wordToSpan.setSpan(
                ForegroundColorSpan(Color.parseColor("#464646")),
                0,
                wordToSpan.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            comment.setFilters(arrayOf<InputFilter>(LengthFilter(100 + saveComment!!.length)))

//            Log.d("saveComment", saveComment.toString())
//            Log.d("saveComment len", saveComment!!.length.toString())
            comment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    // saveComment = commentList[adapterPosition].comment
                    commentList[adapterPosition].comment = p0?.toString()
                    Log.d("saveComment", saveComment.toString())
                    Log.d("saveComment len", saveComment!!.length.toString())
                    input = commentList[adapterPosition].comment?.substring(saveComment!!.length)
                    Log.d("input", input.toString())

                    if (input?.length!! > 0) {
                        number.setBackgroundResource(R.drawable.ic_select_oval)
                    } else {
                        number.setBackgroundResource(R.drawable.ic_oval)
                    }
                    if (select_cut > adapterPosition) {
                        if (!p0.toString().startsWith(saveComment.toString())) {
                            comment!!.setText(wordToSpan)
                            Selection.setSelection(comment.text, comment.length())
                            comment.setTextColor(Color.parseColor("#FF7051"))
                        }
                    } else {
                        // commentList[adapterPosition].comment = p0.toString()
                        comment.setTextColor(Color.parseColor("#FF7051"))
                    }
                    // saveComment = commentList[adapterPosition].comment
                    // input = commentList[adapterPosition].comment?.substring(saveComment!!.length)
                    // commentList[adapterPosition].comment = p0.toString()
                    len.setText("글자수 : " + input?.length.toString() + "/100")
//                    Log.d("input len", input?.length.toString())
//                    Log.d("input value", input.toString())

                    Log.d("commentList", commentList[adapterPosition].comment.toString())
                }
            })


        }

    }

}
