package com.example.testpayday.presentation.dashboard.entity

import androidx.annotation.ColorRes

sealed class DashboardUiEntity {
    data class DashboardItemUiEntity(
        val amount: String,
        val category: String,
        @ColorRes val amountColorRes: Int
    ) : DashboardUiEntity()

    data class DashboardMonthHeaderUiEntity(
        val date: String,
        val amount: String,
        @ColorRes val amountColorRes: Int
    ) : DashboardUiEntity()
}
