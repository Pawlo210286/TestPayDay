package com.example.testpayday.presentation.transactions.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testpayday.R
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity
import kotlinx.android.synthetic.main.list_item_transaction_title.view.*

class TitleHolder(
    view: View
) : TransactionHolder<TransactionUiEntity.TransactionMonthHeaderUiEntity>(view) {

    override fun bind(item: TransactionUiEntity.TransactionMonthHeaderUiEntity) {
        itemView.month_title.text = item.date
    }

    companion object {
        fun create(parent: ViewGroup): TitleHolder {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_transaction_title, parent, false).let {
                    TitleHolder(it)
                }
        }
    }
}
