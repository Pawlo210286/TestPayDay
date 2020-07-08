package com.example.testpayday.presentation.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.core.library.recycler.Submittable
import com.example.testpayday.presentation.dashboard.adapter.holder.DashboardCategoryHolder
import com.example.testpayday.presentation.dashboard.adapter.holder.DashboardHolder
import com.example.testpayday.presentation.dashboard.adapter.holder.DashboardTitleHolder
import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity

class DashboardAdapter : RecyclerView.Adapter<DashboardHolder<*>>(), Submittable<DashboardUiEntity> {

    private var data: List<DashboardUiEntity> = emptyList()

    override fun submitList(list: List<DashboardUiEntity>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder<*> {
        return when (ViewType.values()[viewType]) {
            ViewType.TITLE -> DashboardTitleHolder.create(parent)
            ViewType.DATA -> DashboardCategoryHolder.create(parent)
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DashboardHolder<*>, position: Int) {
        when (holder) {
            is DashboardTitleHolder -> {
                data[position].let {
                    if (it is DashboardUiEntity.DashboardMonthHeaderUiEntity) {
                        holder.bind(it)
                    }
                }
            }
            is DashboardCategoryHolder -> {
                data[position].let {
                    if (it is DashboardUiEntity.DashboardItemUiEntity) {
                        holder.bind(it)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DashboardUiEntity.DashboardItemUiEntity -> ViewType.DATA
            else -> ViewType.TITLE
        }.ordinal
    }

    enum class ViewType {
        TITLE, DATA
    }
}
