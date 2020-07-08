package com.example.testpayday.presentation.dashboard.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testpayday.R
import com.example.testpayday.core.library.resources.getColor
import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity
import kotlinx.android.synthetic.main.list_item_dashboard_date.view.*

class DashboardTitleHolder(
    view: View
) : DashboardHolder<DashboardUiEntity.DashboardMonthHeaderUiEntity>(view) {

    override fun bind(item: DashboardUiEntity.DashboardMonthHeaderUiEntity) = with(itemView) {
        month_title.text = item.date
        amount.text = item.amount
        amount.setTextColor(getColor(item.amountColorRes))
    }

    companion object {
        fun create(parent: ViewGroup): DashboardTitleHolder {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_dashboard_date, parent, false).let {
                    DashboardTitleHolder(it)
                }
        }
    }
}
