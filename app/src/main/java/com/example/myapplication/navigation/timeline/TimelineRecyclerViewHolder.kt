/**
 *  받아온 데이터를 구현한 UI의 View에 넣어주는 클래스
 */
package com.example.myapplication.navigation.timeline

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO

class TimelineRecyclerViewHolder(v : View,
                                 recyclerInterface: TimelineRecyclerInterface) : RecyclerView.ViewHolder(v),
                                View.OnClickListener
{
    private val title = v.findViewById<TextView>(R.id.tv_title)
    private val subtitle = v.findViewById<TextView>(R.id.tv_subtitle)
    private val image = v.findViewById<ImageView>(R.id.iv_image)
    private val btn_delete = v.findViewById<Button>(R.id.btn_delete)
    private val btn_modify = v.findViewById<Button>(R.id.btn_modify)

    private var myInterface : TimelineRecyclerInterface? = null

    //기본 생성자
    init {
        v.setOnClickListener(this)
        this.myInterface = recyclerInterface
    }

    fun bind(data : RecipeDTO.PostItem){
        title.text = data.get(0).title
        subtitle.text = data.get(0).subTitle
        Glide.with(App.instance).load(data.get(0).imageUrl).into(image);

        //TODO(삭제 버튼 클릭했을 때 해당 데이터 지우는 코드 )
        btn_delete.setOnClickListener{
            Toast.makeText(
                App.instance,
                "id ${data.get(0).id}번의 삭제 버튼 클릭 ",
                Toast.LENGTH_SHORT
            ).show()
        }

        btn_modify.setOnClickListener{
            Toast.makeText(
                App.instance,
                "id ${data.get(0).id}번의 수정 버튼 클릭 ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onClick(p0: View?) {
        this.myInterface?.onItemClicked(adapterPosition)
    }
}

