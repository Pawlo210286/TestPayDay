package com.example.testpayday.domain.interactor

import com.example.testpayday.core.base.data.network.Result
import com.example.testpayday.domain.PayDayRepository
import com.example.testpayday.domain.RuntimeRepository
import com.example.testpayday.domain.entity.Account
import com.example.testpayday.domain.entity.toAccount

class AccountInteractor(
    private val remote: PayDayRepository,
    private val runtime: RuntimeRepository
) {
    suspend fun getUserAccounts(): List<Account> {
        return runtime.getCurrentUser().id.let {
            remote.getUserAccounts(customerId = it)
        }.takeIf {
            it is Result.Success
        }?.let {
            it as Result.Success
            it.data
                .map { it.toAccount() }
        }.orEmpty()
    }
}
