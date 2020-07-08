package com.example.testpayday.domain.interactor

import com.example.testpayday.core.base.data.network.Result
import com.example.testpayday.domain.PayDayRepository
import com.example.testpayday.domain.RuntimeRepository
import com.example.testpayday.domain.entity.Transaction
import com.example.testpayday.domain.entity.toTransaction

class TransactionsInteractor(
    private val remote: PayDayRepository,
    private val runtime: RuntimeRepository
) {

    suspend fun getMyTransactions(): List<Transaction> {
        return runtime.getCurrentUser().id.let {
            remote.getTransactionsForUser(it)
        }.takeIf {
            it is Result.Success
        }?.let {
            it as Result.Success
            it.data
                .map { it.toTransaction() }
                .sortedByDescending { it.date }
        }.orEmpty().sortedByDescending {
            it.date
        }
    }
}
