package com.example.testpayday.presentation.dashboard

import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity

data class DashboardViewState(
    val isRefreshing: Boolean,
    val isEmptyContainerVisible: Boolean,
    val isErrorContainerVisible: Boolean,
    val dashboardList: List<DashboardUiEntity>
)
