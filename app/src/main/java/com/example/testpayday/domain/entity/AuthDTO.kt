package com.example.testpayday.domain.entity

import org.joda.time.DateTime

class AuthDTO(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
    val gender: String,
    val dob: DateTime,
    val email: String
)
