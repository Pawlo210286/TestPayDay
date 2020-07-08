package com.example.testpayday.domain.entity

import com.example.testpayday.data.network.response.AccountResponse
import com.example.testpayday.data.network.response.TransactionResponse
import com.example.testpayday.presentation.utils.DEFAULT_DATE_FORMAT
import org.joda.time.DateTime
import org.joda.time.LocalDate

fun TransactionResponse.toTransaction(): Transaction {
    return Transaction(
        id = id?.toLong() ?: -1L,
        amount = amount ?: 0.0,
        date = DateTime.parse(date.orEmpty()),
        accountId = accountId ?: -1L,
        category = category.orEmpty(),
        vendor = vendor.orEmpty()
    )
}

fun AccountResponse.toAccount(): Account {
    return Account(
        id = id ?: -1L,
        active = active ?: false,
        customerId = customerId,
        dateCreated = try {
            DEFAULT_DATE_FORMAT.parse(dateCreated.orEmpty()).let {
                LocalDate.fromDateFields(it)
            }.let {
                DateTime.now().withDate(it)
            }
        } catch (e: Exception) {
            DateTime.now()
        },
        iban = iban.orEmpty(),
        type = type.orEmpty()
    )
}
