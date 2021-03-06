package com.example.yorijori.navigation.upload

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.yorijori.App
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class UploadSwapDelete(context: Context, dragDir: Int, swipeDir: Int) :
    ItemTouchHelper.SimpleCallback(
        dragDir,
        swipeDir
    ) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addActionIcon(R.drawable.ic_menu_delete)
            .addBackgroundColor(ContextCompat.getColor(App.instance, R.color.holo_red_dark))
            .addSwipeLeftBackgroundColor(Color.parseColor("#777777"))
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}