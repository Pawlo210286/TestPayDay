package com.example.testpayday.data.network.api

import com.example.testpayday.data.network.request.AuthenticateRequest
import com.example.testpayday.data.network.request.CreateAccountRequest
import com.example.testpayday.data.network.response.AccountResponse
import com.example.testpayday.data.network.response.AuthenticateResponse
import com.example.testpayday.data.network.response.CustomerResponse
import com.example.testpayday.data.network.response.TransactionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PayDayApi {

    @POST(AUTH)
    fun authenticateAsync(
        @Body
        request: AuthenticateRequest
    ): Deferred<Response<AuthenticateResponse>>

    @GET(CUSTOMERS)
    fun getCustomersAsync(): Deferred<Response<List<CustomerResponse>>>

    @GET(ACCOUNTS)
    fun getUserAccountsAsync(@Query("customer_id") customerId: Long): Deferred<Response<List<AccountResponse>>>

    @GET(TRANSACTIONS)
    fun getTransactionsAsync(): Deferred<Response<List<TransactionResponse>>>

    @GET(TRANSACTIONS)
    fun getTransactionsForUserAsync(
        @Query("account_id")
        userId: Long
    ): Deferred<Response<List<TransactionResponse>>>

    @POST(CUSTOMERS)
    fun createAccountAsync(
        @Body
        request: CreateAccountRequest
    ): Deferred<Response<AuthenticateResponse>>

    companion object {
        private const val AUTH = "authenticate"
        private const val CUSTOMERS = "customers"
        private const val ACCOUNTS = "accounts"
        private const val TRANSACTIONS = "transactions"
    }
}
