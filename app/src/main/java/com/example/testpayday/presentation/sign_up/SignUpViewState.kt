package com.example.testpayday.presentation.sign_up

import org.joda.time.DateTime

data class SignUpViewState(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
    val gender: String,
    val dob: DateTime?,
    val email: String
)
