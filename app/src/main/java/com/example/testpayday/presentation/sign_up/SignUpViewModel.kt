package com.example.testpayday.presentation.sign_up

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.viewmodel.BaseViewModel
import com.example.testpayday.core.base.presentation.viewmodel.ErrorMessageEvent
import com.example.testpayday.core.library.livedata.delegate
import com.example.testpayday.core.library.livedata.mapDistinct
import com.example.testpayday.domain.entity.AuthDTO
import com.example.testpayday.domain.interactor.AuthInteractor
import com.example.testpayday.presentation.login.LoginRouter
import com.example.testpayday.presentation.utils.DATE_FORMAT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime

class SignUpViewModel(
    private val authInteractor: AuthInteractor,
    private val router: LoginRouter
) : BaseViewModel() {

    private val liveState = MutableLiveData<SignUpViewState>(createInitialViewState())
    private var viewState: SignUpViewState by liveState.delegate()

    val dobText = liveState.mapDistinct { state ->
        DATE_FORMAT.format(state.dob?.toDate() ?: return@mapDistinct "")
    }

    fun onEmailChanged(value: String) {
        viewState = viewState.copy(
            email = value
        )
    }

    fun onPasswordChanged(value: String) {
        viewState = viewState.copy(
            password = value
        )
    }

    fun onFirstNameChanged(value: String) {
        viewState = viewState.copy(
            firstName = value
        )
    }

    fun onLastNameChanged(value: String) {
        viewState = viewState.copy(
            lastName = value
        )
    }

    fun onGenderChanged(value: String) {
        viewState = viewState.copy(
            gender = value
        )
    }

    fun onDobChanged(value: DateTime) {
        viewState = viewState.copy(
            dob = value
        )
    }

    fun onPhoneChanged(value: String) {
        viewState = viewState.copy(
            phone = value
        )
    }

    private fun createInitialViewState(): SignUpViewState {
        return SignUpViewState(
            password = "",
            email = "",
            phone = "",
            gender = "",
            firstName = "",
            dob = null,
            lastName = ""
        )
    }

    fun onContinueClicked() {
        if (isDataValid()) {
            createAccount()
        } else {
            eventsQueue.offer(ErrorMessageEvent(R.string.error_invalid_data))
        }
    }

    private fun createAccount() {
        scope.launch {
            try {
                AuthDTO(
                    firstName = viewState.firstName,
                    phone = viewState.phone,
                    dob = viewState.dob!!,
                    gender = viewState.gender,
                    lastName = viewState.lastName,
                    email = viewState.email,
                    password = viewState.email
                ).let {
                    authInteractor.createAccount(it)
                }.takeIf {
                    it
                }?.let {
                    withContext(Dispatchers.Main) {
                        router.navigateToMain()
                    }
                } ?: run {
                    withContext(Dispatchers.Main) {
                        eventsQueue.offer(ErrorMessageEvent(R.string.error_message))
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    eventsQueue.offer(ErrorMessageEvent(R.string.error_message))
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        return viewState.email.let {
            !TextUtils.isEmpty(it) && android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
        }
    }

    private fun validatePassword(): Boolean {
        return viewState.password.length >= 6
    }

    private fun isDataValid(): Boolean {
        return viewState.dob != null &&
            validateEmail() &&
            validatePassword() &&
            viewState.firstName.isNotEmpty() &&
            viewState.lastName.isNotEmpty() &&
            viewState.phone.isNotEmpty() &&
            viewState.gender.isNotEmpty()
    }
}
