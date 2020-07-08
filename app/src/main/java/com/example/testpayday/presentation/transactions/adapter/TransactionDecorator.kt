package com.example.testpayday.presentation.transactions.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils.dpToPx

class TransactionDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        parent.getChildAdapterPosition(view).let { position ->
            if (position == 0) {
                outRect.top = dpToPx(
                    view.context,
                    TOP_MARGIN
                ).toInt()
                return
            }

            if (parent.adapter?.getItemViewType(position) == TransactionsAdapter.ViewType.TITLE.ordinal) {
                outRect.top = dpToPx(
                    view.context,
                    TITLE_MARGIN
                ).toInt()
            } else {
                outRect.top = dpToPx(
                    view.context,
                    DATA_MARGIN
                ).toInt()
            }
        }
    }

    companion object {
        private const val TOP_MARGIN = 24
        private const val TITLE_MARGIN = 16
        private const val DATA_MARGIN = 8
    }
}
