package com.example.testpayday.presentation.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.BaseFragment
import com.example.testpayday.core.base.presentation.ViewModelFactory
import com.example.testpayday.core.library.livedata.observe
import com.example.testpayday.core.library.viewmodel.bindViewModel
import com.example.testpayday.core.library.viewmodel.kodeinViewModel
import com.example.testpayday.presentation.dashboard.adapter.DashboardAdapter
import com.example.testpayday.presentation.dashboard.adapter.DelimeterItemDecoration
import com.example.testpayday.presentation.dashboard.entity.DashboardUiEntity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.emptyContainer
import kotlinx.android.synthetic.main.fragment_dashboard.errorContainer
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.fragment_transactions.swipeToRefresh
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel: DashboardViewModel by kodeinViewModel()
    private val adapter = DashboardAdapter()

    override val kodeinModule = Kodein.Module(DashboardFragment::class.java.simpleName) {
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bindViewModel<DashboardViewModel>() with provider {
            DashboardViewModel(dashboard = instance())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.isRefreshing, ::renderRefreshingState)
        observe(viewModel.dashboardList, ::renderDashboardList)
        observe(viewModel.isEmptyContainerVisible, ::renderEmptyContainerState)
        observe(viewModel.isErrorContainerVisible, ::renderErrorContainerState)
    }

    private fun initViews() {
        dashboardList.adapter = adapter
//        dashboardList.addItemDecoration(TransactionDecorator())
        dashboardList.addItemDecoration(DelimeterItemDecoration())
        swipeToRefresh.setOnRefreshListener { viewModel.refreshData() }
    }

    private fun renderRefreshingState(isRefreshing: Boolean) {
        swipeToRefresh.isRefreshing = isRefreshing
    }

    private fun renderDashboardList(list: List<DashboardUiEntity>) {
        adapter.submitList(list)
    }

    private fun renderEmptyContainerState(isVisible: Boolean) {
        emptyContainer.isVisible = isVisible
    }

    private fun renderErrorContainerState(isVisible: Boolean) {
        errorContainer.isVisible = isVisible
    }

    companion object {
        fun newInstance() = DashboardFragment()
    }
}
