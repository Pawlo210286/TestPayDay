package com.example.testpayday.data

import com.example.testpayday.core.base.data.BaseRepository
import com.example.testpayday.core.base.data.network.Result
import com.example.testpayday.data.network.api.PayDayApi
import com.example.testpayday.data.network.request.AuthenticateRequest
import com.example.testpayday.data.network.request.CreateAccountRequest
import com.example.testpayday.data.network.response.AccountResponse
import com.example.testpayday.data.network.response.AuthenticateResponse
import com.example.testpayday.data.network.response.TransactionResponse
import com.example.testpayday.domain.PayDayRepository

class PayDayRemoteRepository(
    private val api: PayDayApi
) : BaseRepository(), PayDayRepository {

    override suspend fun getTransactionsForUser(userId: Long): Result<List<TransactionResponse>> {
        return execute {
            api.getTransactionsForUserAsync(userId)
        }
    }

    override suspend fun login(request: AuthenticateRequest): Result<AuthenticateResponse> {
        return execute {
            api.authenticateAsync(request)
        }
    }

    override suspend fun createAccount(request: CreateAccountRequest): Result<AuthenticateResponse> {
        return execute {
            api.createAccountAsync(request)
        }
    }

    override suspend fun getUserAccounts(customerId: Long): Result<List<AccountResponse>> {
        return execute {
            api.getUserAccountsAsync(customerId)
        }
    }
}
