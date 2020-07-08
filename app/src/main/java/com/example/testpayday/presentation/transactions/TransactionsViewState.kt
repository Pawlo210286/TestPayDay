package com.example.testpayday.presentation.transactions

import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity

data class TransactionsViewState(
    val isRefreshing: Boolean,
    val isEmptyContainerVisible: Boolean,
    val isErrorContainerVisible: Boolean,
    val transactions: List<TransactionUiEntity>
)
