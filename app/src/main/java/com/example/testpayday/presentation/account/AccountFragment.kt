package com.example.testpayday.presentation.account

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
import com.example.testpayday.presentation.account.adapter.AccountsAdapter
import com.example.testpayday.presentation.account.entity.AccountUiEntity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val viewModel: AccountViewModel by kodeinViewModel()
    private val adapter = AccountsAdapter()

    override val kodeinModule = Kodein.Module(AccountFragment::class.java.simpleName) {
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bindViewModel<AccountViewModel>() with provider {
            AccountViewModel(account = instance())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.isRefreshing, ::renderRefreshingState)
        observe(viewModel.accounts, ::renderAccounts)
        observe(viewModel.isEmptyContainerVisible, ::renderEmptyContainerState)
        observe(viewModel.isErrorContainerVisible, ::renderErrorContainerState)
    }

    private fun initViews() {
        dashboardList.adapter = adapter
    }

    private fun renderRefreshingState(isRefreshing: Boolean) {
        showModalProgress = isRefreshing
    }

    private fun renderAccounts(list: List<AccountUiEntity>) {
        adapter.submitList(list)
    }

    private fun renderEmptyContainerState(isVisible: Boolean) {
        emptyContainer.isVisible = isVisible
    }

    private fun renderErrorContainerState(isVisible: Boolean) {
        errorContainer.isVisible = isVisible
    }

    companion object {
        fun newInstance() = AccountFragment()
    }
}
