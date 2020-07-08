package com.example.testpayday.domain

import com.example.testpayday.core.base.data.network.Result
import com.example.testpayday.data.network.request.AuthenticateRequest
import com.example.testpayday.data.network.request.CreateAccountRequest
import com.example.testpayday.data.network.response.AccountResponse
import com.example.testpayday.data.network.response.AuthenticateResponse
import com.example.testpayday.data.network.response.TransactionResponse

interface PayDayRepository {

    suspend fun getTransactionsForUser(userId: Long): Result<List<TransactionResponse>>
    suspend fun login(request: AuthenticateRequest): Result<AuthenticateResponse>
    suspend fun createAccount(request: CreateAccountRequest): Result<AuthenticateResponse>
    suspend fun getUserAccounts(customerId: Long): Result<List<AccountResponse>>
}
