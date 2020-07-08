package com.example.testpayday.domain.entity

import org.joda.time.DateTime

data class Account(
    val id: Long,
    val active: Boolean?,
    val customerId: Long?,
    val dateCreated: DateTime?,
    val iban: String,
    val type: String
)
