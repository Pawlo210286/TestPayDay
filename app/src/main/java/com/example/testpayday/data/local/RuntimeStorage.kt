package com.example.testpayday.data.local

import com.example.testpayday.domain.RuntimeRepository
import com.example.testpayday.domain.entity.User

class RuntimeStorage : RuntimeRepository {

    private var currentUser: User? = null

    override fun getCurrentUser(): User {
        return currentUser ?: User.empty()
    }

    override fun updateCurrentUser(user: User) {
        currentUser = user
    }
}
