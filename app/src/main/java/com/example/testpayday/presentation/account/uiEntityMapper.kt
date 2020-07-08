package com.example.testpayday.presentation.account

import com.example.testpayday.domain.entity.Account
import com.example.testpayday.presentation.account.entity.AccountUiEntity
import com.example.testpayday.presentation.utils.DATE_FORMAT
import java.util.*

fun Account.toUiEntity(): AccountUiEntity {
    return AccountUiEntity(
        isActive = active.toString(),
        dateCreated = DATE_FORMAT.format(dateCreated?.toDate() ?: Date()),
        iban = iban,
        type = type
    )
}
