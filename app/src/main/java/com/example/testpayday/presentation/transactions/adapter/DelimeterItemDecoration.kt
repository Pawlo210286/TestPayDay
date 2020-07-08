package com.example.testpayday.presentation.transactions.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.R
import com.example.testpayday.presentation.dashboard.adapter.DashboardAdapter


class DelimeterItemDecoration(
    @ColorInt tint: Int? = null
) : RecyclerView.ItemDecoration() {

    private val divider: Drawable = ColorDrawable(tint ?: Color.LTGRAY)
    private val h =
        DELIMITER_H

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        parent.getChildAdapterPosition(view).let { position ->
            check(parent, position) {
                outRect.bottom = h
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + h

            check(parent, parent.getChildAdapterPosition(parent.getChildAt(i))) {
                drawDelimiter(
                    left + calculateLeftOffset(parent, parent.getChildAdapterPosition(parent.getChildAt(i))),
                    top,
                    right,
                    bottom,
                    c
                )
            }
        }
    }

    private fun check(parent: RecyclerView, position: Int, block: () -> Unit) {
        if (parent.adapter?.getItemViewType(position) == TransactionsAdapter.ViewType.TITLE.ordinal) {
            block()
        }
    }

    private fun calculateLeftOffset(parent: RecyclerView, position: Int): Int {
        return if (parent.adapter?.getItemViewType(position) == DashboardAdapter.ViewType.TITLE.ordinal) {
            parent.resources.getDimension(R.dimen.dimen_8dp).toInt()
        } else {
            DEFAULT_LEFT_OFFSET
        }
    }

    private fun drawDelimiter(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        c: Canvas
    ) {
        divider.setBounds(left, top, right, bottom)
        divider.draw(c)
    }

    companion object {
        private const val DELIMITER_H = 2
        private const val DEFAULT_LEFT_OFFSET = 0
    }
}
