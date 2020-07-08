package com.example.testpayday.presentation.transactions.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity

abstract class TransactionHolder<T : TransactionUiEntity>(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
}
