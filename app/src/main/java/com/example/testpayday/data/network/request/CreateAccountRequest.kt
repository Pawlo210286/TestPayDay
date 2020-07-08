package com.example.testpayday.data.network.request


import com.google.gson.annotations.SerializedName

data class CreateAccountRequest(
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("First Name")
    val firstName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("Last Name")
    val lastName: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?
)
