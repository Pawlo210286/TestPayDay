package com.example.testpayday.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.viewmodel.BaseViewModel
import com.example.testpayday.core.library.livedata.delegate
import com.example.testpayday.core.library.livedata.mapDistinct
import com.example.testpayday.domain.interactor.DashboardInteractor
import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity
import com.example.testpayday.presentation.utils.MONEY_FORMAT
import com.example.testpayday.presentation.utils.MONTH_YEAR_DATE_FORMAT
import kotlinx.coroutines.launch
import timber.log.Timber

class DashboardViewModel(
    private val dashboard: DashboardInteractor
) : BaseViewModel() {

    private val liveState = MutableLiveData<DashboardViewState>(createInitialViewState())
    private var viewState: DashboardViewState by liveState.delegate()
    val isRefreshing = liveState.mapDistinct { it.isRefreshing }
    val dashboardList = liveState.mapDistinct { it.dashboardList }
    val isEmptyContainerVisible = liveState.mapDistinct { it.isEmptyContainerVisible }
    val isErrorContainerVisible = liveState.mapDistinct { it.isErrorContainerVisible }

    init {
        invalidate()
    }

    fun refreshData() {
        invalidate()
    }

    private fun invalidate() {
        scope.launch {
            try {
                viewState = viewState.copy(isRefreshing = true)
                val dashboardList = loadDashboardUi()
                viewState = viewState.copy(
                    isRefreshing = false,
                    dashboardList = dashboardList,
                    isEmptyContainerVisible = dashboardList.isEmpty()
                )
            } catch (e: Exception) {
                viewState = viewState.copy(
                    isErrorContainerVisible = true,
                    isRefreshing = false
                )
                Timber.e(e)
            }
        }
    }

    private suspend fun loadDashboardUi(): List<DashboardUiEntity> {
        val resultDashboardList = mutableListOf<DashboardUiEntity>()
        val transactions = dashboard.getDashboard()
        viewState = viewState.copy(isRefreshing = true)
        for (transaction in transactions) {
            val monthAmount = transaction.value.sumByDouble { it.amount }
            val dashboardMonth = DashboardUiEntity.DashboardMonthHeaderUiEntity(
                date = MONTH_YEAR_DATE_FORMAT.format(transaction.key.toDate()),
                amount = MONEY_FORMAT.format(monthAmount),
                amountColorRes = if (monthAmount >= 0) R.color.positiveAmount else R.color.negativeAmount
            )
            resultDashboardList.add(dashboardMonth)

            val categories = transaction.value.groupBy { it.category }
            for (category in categories) {
                val categoryAmount = category.value.sumByDouble { it.amount }
                val dashboardCategory = DashboardUiEntity.DashboardItemUiEntity(
                    category = category.key,
                    amount = MONEY_FORMAT.format(categoryAmount),
                    amountColorRes = if (categoryAmount >= 0) R.color.positiveAmount else R.color.negativeAmount
                )
                resultDashboardList.add(dashboardCategory)
            }
        }
        return resultDashboardList
    }

    private fun createInitialViewState(): DashboardViewState {
        return DashboardViewState(
            isRefreshing = false,
            isEmptyContainerVisible = false,
            isErrorContainerVisible = false,
            dashboardList = emptyList()
        )
    }
}
