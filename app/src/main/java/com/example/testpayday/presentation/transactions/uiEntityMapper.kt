package com.example.testpayday.presentation.transactions

import com.example.testpayday.R
import com.example.testpayday.domain.entity.Transaction
import com.example.testpayday.presentation.transactions.entities.TransactionUiEntity
import com.example.testpayday.presentation.utils.DATE_FORMAT
import com.example.testpayday.presentation.utils.MONEY_FORMAT
import com.example.testpayday.presentation.utils.TIME_FORMAT


fun Transaction.toUiEntity(): TransactionUiEntity.TransactionItemUiEntity {
    val convertedDate = date.toDate()
    return TransactionUiEntity.TransactionItemUiEntity(
        amount = MONEY_FORMAT.format(this.amount),
        category = category,
        date = DATE_FORMAT.format(convertedDate),
        vendor = vendor,
        time = TIME_FORMAT.format(convertedDate),
        amountColorRes = if (amount >= 0) R.color.positiveAmount else R.color.negativeAmount
    )
}
