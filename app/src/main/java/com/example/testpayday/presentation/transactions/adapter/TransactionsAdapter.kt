package com.example.testpayday.presentation.transactions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.core.library.recycler.Submittable
import com.example.testpayday.presentation.transactions.adapter.holder.DataHolder
import com.example.testpayday.presentation.transactions.adapter.holder.TitleHolder
import com.example.testpayday.presentation.transactions.adapter.holder.TransactionHolder
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity

class TransactionsAdapter : RecyclerView.Adapter<TransactionHolder<*>>(), Submittable<TransactionUiEntity> {

    private var data: List<TransactionUiEntity> = emptyList()

    override fun submitList(list: List<TransactionUiEntity>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder<*> {
        return when (ViewType.values()[viewType]) {
            ViewType.TITLE -> TitleHolder.create(parent)
            ViewType.DATA -> DataHolder.create(parent)
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TransactionHolder<*>, position: Int) {
        when (holder) {
            is TitleHolder -> {
                data[position].let {
                    if (it is TransactionUiEntity.TransactionMonthHeaderUiEntity) {
                        holder.bind(it)
                    }
                }
            }
            is DataHolder -> {
                data[position].let {
                    if (it is TransactionUiEntity.TransactionItemUiEntity) {
                        holder.bind(it)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is TransactionUiEntity.TransactionItemUiEntity -> ViewType.DATA
            else -> ViewType.TITLE
        }.ordinal
    }

    enum class ViewType {
        TITLE, DATA
    }
}
