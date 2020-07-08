package com.example.testpayday.domain.entity

class User(
    val id: Long
) {
    companion object {
        fun empty() = User(-1L)
    }
}
