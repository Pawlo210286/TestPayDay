package com.example.testpayday.presentation.transactions.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testpayday.R
import com.example.testpayday.core.library.resources.getColor
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity
import kotlinx.android.synthetic.main.list_item_transaction_data.view.*

class DataHolder(
    view: View
) : TransactionHolder<TransactionUiEntity.TransactionItemUiEntity>(view) {

    override fun bind(item: TransactionUiEntity.TransactionItemUiEntity) = with(itemView) {
        category.text = item.category
        vendor.text = item.vendor
        amount.text = item.amount
        amount.setTextColor(itemView.getColor(item.amountColorRes))
        time.text = item.time
    }

    companion object {
        fun create(parent: ViewGroup): DataHolder {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_transaction_data, parent, false).let {
                    DataHolder(it)
                }
        }
    }
}
