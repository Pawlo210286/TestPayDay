package com.example.testpayday.domain.entity

import org.joda.time.DateTime

data class Transaction(
    val id: Long,
    val accountId: Long,
    val amount: Double,
    val category: String,
    val date: DateTime,
    val vendor: String
)
