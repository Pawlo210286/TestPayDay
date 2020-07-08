package com.example.testpayday.presentation.dashboard.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity

abstract class DashboardHolder<T : DashboardUiEntity>(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
}
