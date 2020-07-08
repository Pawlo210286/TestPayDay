package com.example.testpayday.domain.interactor

import com.example.testpayday.core.base.data.network.Result
import com.example.testpayday.data.network.request.AuthenticateRequest
import com.example.testpayday.data.network.request.CreateAccountRequest
import com.example.testpayday.domain.PayDayRepository
import com.example.testpayday.domain.RuntimeRepository
import com.example.testpayday.domain.entity.AuthDTO
import com.example.testpayday.domain.entity.User
import com.example.testpayday.presentation.utils.RESPONSE_DATE_FORMAT

class AuthInteractor(
    private val remote: PayDayRepository,
    private val runtime: RuntimeRepository
) {

    suspend fun login(email: String, pass: String): Boolean {
        AuthenticateRequest(email, pass).let {
            remote.login(it)
        }.takeIf {
            it is Result.Success
        }?.let {
            it as Result.Success

            User(
                id = it.data.id ?: -1L
            )
        }?.let {
            runtime.updateCurrentUser(it)
        }?.let {
            return true
        }

        return false
    }

    suspend fun createAccount(dto: AuthDTO): Boolean {
        CreateAccountRequest(
            dob = RESPONSE_DATE_FORMAT.format(dto.dob.toDate()),
            password = dto.password,
            email = dto.email,
            lastName = dto.lastName,
            gender = dto.gender,
            phone = dto.phone,
            firstName = dto.firstName
        ).let {
            remote.createAccount(it)
        }.takeIf {
            it is Result.Success
        }?.let {
            it as Result.Success

            User(
                id = it.data.id ?: -1L
            )
        }?.let {
            runtime.updateCurrentUser(it)
        }?.let {
            return true
        }

        return false
    }
}
