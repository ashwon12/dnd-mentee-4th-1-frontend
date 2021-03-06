/**
 *  각각의 item을 recyclerview로 연결 시켜주는 adapter class
 */

package com.example.yorijori.navigation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.R
import com.example.yorijori.data.datasource.remote.api.RecipeDTO

class FeedRecyclerAdapter(myInterface: FeedRecyclerInterface) :
    RecyclerView.Adapter<FeedRecyclerViewHolder>() {

    private var myInterface: FeedRecyclerInterface? = null
    private var items = ArrayList<RecipeDTO.RecipeFinal>()

    //생성자
    init {
        this.myInterface = myInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.feed_list_item,
            parent, false
        )
        return FeedRecyclerViewHolder(view, this.myInterface!!)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FeedRecyclerViewHolder, position: Int) {
        val data = items.get(position)
        holder.bind(data)

    }

    fun feedUpdateList(feedItem: List<RecipeDTO.RecipeFinal>) {
        this.items.addAll(feedItem)
    }
}