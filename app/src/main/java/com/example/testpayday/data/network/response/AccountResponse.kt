package com.example.testpayday.data.network.response


import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("customer_id")
    val customerId: Long?,
    @SerializedName("date_created")
    val dateCreated: String?,
    @SerializedName("iban")
    val iban: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("type")
    val type: String?
)
