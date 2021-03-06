package com.googleplay.yorijori.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.googleplay.yorijori.R
import com.googleplay.yorijori.data.datasource.remote.api.RecipeDTO

class DetailCommentAdapter: RecyclerView.Adapter<DetailCommentViewHolder>() {

    private lateinit var view: View

    var comments = ArrayList<RecipeDTO.Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCommentViewHolder {
        view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_comment_of_recipe,
                parent,
                false
            )
        return DetailCommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailCommentViewHolder, position: Int) {
        val data = comments.get(position)
        holder.bindItem(data)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun updateComments(arrComments: ArrayList<RecipeDTO.Comment>) {
        this.comments.clear()
        this.comments.addAll(arrComments)
    }

    fun addComment(comment : RecipeDTO.Comment) {
        this.comments.add(comment)
    }
}