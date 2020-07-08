package com.example.testpayday.presentation.account

import androidx.lifecycle.MutableLiveData
import com.example.testpayday.core.base.presentation.viewmodel.BaseViewModel
import com.example.testpayday.core.library.livedata.delegate
import com.example.testpayday.core.library.livedata.mapDistinct
import com.example.testpayday.domain.interactor.AccountInteractor
import com.example.testpayday.presentation.account.entity.AccountUiEntity
import kotlinx.coroutines.launch
import timber.log.Timber

class AccountViewModel(
    private val account: AccountInteractor
) : BaseViewModel() {

    private val liveState = MutableLiveData<AccountViewState>(createInitialViewState())
    private var viewState: AccountViewState by liveState.delegate()
    val isRefreshing = liveState.mapDistinct { it.isRefreshing }
    val accounts = liveState.mapDistinct { it.accountsList }
    val isEmptyContainerVisible = liveState.mapDistinct { it.isEmptyContainerVisible }
    val isErrorContainerVisible = liveState.mapDistinct { it.isErrorContainerVisible }

    init {
        invalidate()
    }

    private fun invalidate() {
        scope.launch {
            try {
                viewState = viewState.copy(isRefreshing = true)
                val accounts = loadAccountsUi()
                viewState = viewState.copy(
                    isRefreshing = false,
                    accountsList = accounts,
                    isEmptyContainerVisible = accounts.isEmpty()
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

    private suspend fun loadAccountsUi(): List<AccountUiEntity> {
        return account.getUserAccounts().map { it.toUiEntity() }
    }

    private fun createInitialViewState(): AccountViewState {
        return AccountViewState(
            isRefreshing = false,
            isEmptyContainerVisible = false,
            isErrorContainerVisible = false,
            accountsList = emptyList()
        )
    }
}
