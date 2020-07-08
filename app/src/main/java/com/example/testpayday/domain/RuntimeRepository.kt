package com.example.testpayday.domain

import com.example.testpayday.domain.entity.User

interface RuntimeRepository {

    fun getCurrentUser(): User
    fun updateCurrentUser(user: User)
}
