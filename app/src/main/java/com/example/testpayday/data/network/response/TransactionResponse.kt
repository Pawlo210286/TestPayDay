package com.example.testpayday.data.network.response


import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("account_id")
    val accountId: Long?,
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("vendor")
    val vendor: String?
)
