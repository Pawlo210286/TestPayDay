package com.example.testpayday.presentation.account

import com.example.testpayday.presentation.account.entity.AccountUiEntity

data class AccountViewState(
    val isRefreshing: Boolean,
    val isEmptyContainerVisible: Boolean,
    val isErrorContainerVisible: Boolean,
    val accountsList: List<AccountUiEntity>
)
