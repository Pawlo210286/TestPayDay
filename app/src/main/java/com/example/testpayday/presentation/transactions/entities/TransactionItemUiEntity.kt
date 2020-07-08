package com.example.testpayday.presentation.transactions.entities

import androidx.annotation.ColorRes

sealed class TransactionUiEntity {
    data class TransactionItemUiEntity(
        val amount: String,
        val category: String,
        val date: String,
        val vendor: String,
        val time: String,
        @ColorRes val amountColorRes: Int
    ) : TransactionUiEntity()

    data class TransactionMonthHeaderUiEntity(
        val date: String
    ) : TransactionUiEntity()
}
