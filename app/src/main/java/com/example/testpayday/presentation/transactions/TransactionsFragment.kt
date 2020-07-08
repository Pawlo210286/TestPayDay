package com.example.testpayday.presentation.transactions

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
import com.example.testpayday.presentation.dashboard.DashboardFragment
import com.example.testpayday.presentation.transactions.adapter.DelimeterItemDecoration
import com.example.testpayday.presentation.transactions.adapter.TransactionDecorator
import com.example.testpayday.presentation.transactions.adapter.TransactionsAdapter
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity
import kotlinx.android.synthetic.main.fragment_transactions.*
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class TransactionsFragment : BaseFragment(R.layout.fragment_transactions) {

    override val kodeinModule = Kodein.Module(DashboardFragment::class.java.simpleName) {
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bindViewModel<TransactionsViewModel>() with provider {
            TransactionsViewModel(
                getTransactions = instance()
            )
        }
    }

    private val viewModel: TransactionsViewModel by kodeinViewModel()
    private val adapter = TransactionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.transactions, ::renderTransactions)
        observe(viewModel.isRefreshing, ::renderRefreshingState)
        observe(viewModel.isEmptyContainerVisible, ::renderEmptyContainerState)
        observe(viewModel.isErrorContainerVisible, ::renderErrorContainerState)
    }

    private fun initViews() {
        transactionsList.adapter = adapter
        transactionsList.addItemDecoration(TransactionDecorator())
        transactionsList.addItemDecoration(DelimeterItemDecoration())

        swipeToRefresh.setOnRefreshListener { viewModel.refreshData() }
    }

    private fun renderTransactions(transactions: List<TransactionUiEntity>) {
        adapter.submitList(transactions)
    }

    private fun renderRefreshingState(isRefreshing: Boolean) {
        swipeToRefresh.isRefreshing = isRefreshing
    }

    private fun renderEmptyContainerState(isVisible: Boolean) {
        emptyContainer.isVisible = isVisible
    }

    private fun renderErrorContainerState(isVisible: Boolean) {
        errorContainer.isVisible = isVisible
    }

    companion object {
        fun newInstance() = TransactionsFragment()
    }
}
