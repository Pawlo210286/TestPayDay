package com.example.testpayday.presentation.dashboard.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testpayday.R
import com.example.testpayday.core.library.resources.getColor
import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity
import kotlinx.android.synthetic.main.list_item_dashboard_category.view.*

class DashboardCategoryHolder(
    view: View
) : DashboardHolder<DashboardUiEntity.DashboardItemUiEntity>(view) {

    override fun bind(item: DashboardUiEntity.DashboardItemUiEntity) = with(itemView) {
        category.text = item.category
        amount.text = item.amount
        amount.setTextColor(getColor(item.amountColorRes))
    }

    companion object {
        fun create(parent: ViewGroup): DashboardCategoryHolder {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_dashboard_category, parent, false).let {
                    DashboardCategoryHolder(it)
                }
        }
    }
}
