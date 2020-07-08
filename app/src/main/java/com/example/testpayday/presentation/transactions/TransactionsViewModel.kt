package com.example.testpayday.presentation.transactions

import androidx.lifecycle.MutableLiveData
import com.example.testpayday.core.base.presentation.viewmodel.BaseViewModel
import com.example.testpayday.core.library.livedata.delegate
import com.example.testpayday.core.library.livedata.mapDistinct
import com.example.testpayday.domain.interactor.TransactionsInteractor
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class TransactionsViewModel(
    private val getTransactions: TransactionsInteractor
) : BaseViewModel() {

    private val liveState = MutableLiveData<TransactionsViewState>(createInitialViewState())
    private var viewState: TransactionsViewState by liveState.delegate()

    val transactions = liveState.mapDistinct { it.transactions }
    val isRefreshing = liveState.mapDistinct { it.isRefreshing }
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
                viewState = viewState.copy(
                    isRefreshing = true
                )
                val transactions = calculateTransactionUIList()
                viewState = viewState.copy(
                    isRefreshing = false,
                    transactions = transactions,
                    isEmptyContainerVisible = transactions.isEmpty()
                )
            } catch (e: Exception) {
                viewState = viewState.copy(
                    isRefreshing = false,
                    isErrorContainerVisible = true
                )
            }
        }
    }

    private fun isTheSameDay(first: DateTime, second: DateTime): Boolean {
        return first.toLocalDate() == second.toLocalDate()
    }

    private suspend fun calculateTransactionUIList(): List<TransactionUiEntity> {
        var last: DateTime? = null
        return (getTransactions.getMyTransactions().map { transaction ->
            last?.let {
                val uiTransactionEntity = transaction.toUiEntity()
                if (isTheSameDay(it, transaction.date)) {
                    listOf(uiTransactionEntity)
                } else {
                    last = transaction.date
                    listOf(
                        TransactionUiEntity.TransactionMonthHeaderUiEntity(uiTransactionEntity.date),
                        uiTransactionEntity
                    )
                }
            } ?: run {
                val uiTransactionEntity = transaction.toUiEntity()
                last = transaction.date
                listOf(
                    TransactionUiEntity.TransactionMonthHeaderUiEntity(uiTransactionEntity.date),
                    uiTransactionEntity
                )
            }
        }).flatten()
    }

    private fun createInitialViewState(): TransactionsViewState {
        return TransactionsViewState(
            isRefreshing = false,
            isEmptyContainerVisible = false,
            isErrorContainerVisible = false,
            transactions = emptyList()
        )
    }
}
