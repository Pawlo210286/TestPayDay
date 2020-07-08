package com.example.testpayday.presentation.account.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.R
import com.example.testpayday.presentation.account.entity.AccountUiEntity
import kotlinx.android.synthetic.main.list_item_account.view.*

class AccountHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: AccountUiEntity) {
        with(itemView) {
            ibanValue.text = item.iban
            typeValue.text = item.type
            createdValue.text = item.dateCreated
            activeValue.text = item.isActive
        }
    }

    companion object {
        fun create(parent: ViewGroup): AccountHolder {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_account, parent, false).let {
                    AccountHolder(it)
                }
        }
    }
}
