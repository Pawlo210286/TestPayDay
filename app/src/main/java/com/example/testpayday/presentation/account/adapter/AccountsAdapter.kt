package com.example.testpayday.presentation.account.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.core.library.recycler.Submittable
import com.example.testpayday.presentation.account.entity.AccountUiEntity

class AccountsAdapter : RecyclerView.Adapter<AccountHolder>(), Submittable<AccountUiEntity> {

    private var data: List<AccountUiEntity> = emptyList()

    override fun submitList(list: List<AccountUiEntity>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHolder {
        return AccountHolder.create(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AccountHolder, position: Int) {
        holder.bind(data[position])
    }
}
