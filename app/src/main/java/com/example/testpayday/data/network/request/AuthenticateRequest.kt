package com.example.testpayday.data.network.request

import com.google.gson.annotations.SerializedName

data class AuthenticateRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
