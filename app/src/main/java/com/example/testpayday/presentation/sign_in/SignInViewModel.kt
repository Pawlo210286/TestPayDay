package com.example.testpayday.presentation.sign_in

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.viewmodel.BaseViewModel
import com.example.testpayday.core.base.presentation.viewmodel.ErrorMessageEvent
import com.example.testpayday.core.library.livedata.delegate
import com.example.testpayday.domain.interactor.AuthInteractor
import com.example.testpayday.presentation.login.LoginRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(
    private val router: LoginRouter,
    private val authInteractor: AuthInteractor
) : BaseViewModel() {

    private val liveState = MutableLiveData<SignInViewState>(createInitialViewState())
    private var viewState: SignInViewState by liveState.delegate()

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

    fun onContinueClicked() {
        if (!validateEmail() || !validatePassword()) {
            eventsQueue.offer(ErrorMessageEvent(R.string.error_invalid_data))
            return
        }

        login()
    }

    fun onSignUpClicked() {
        router.navigateToSignUp()
    }

    private fun validateEmail(): Boolean {
        return viewState.email.let {
            !TextUtils.isEmpty(it) && android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
        }
    }

    private fun validatePassword(): Boolean {
        return viewState.password.length >= 6
    }

    private fun login() {
        scope.launch {
            try {
                authInteractor.login(viewState.email, viewState.password).takeIf {
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

    private fun createInitialViewState(): SignInViewState {
        return SignInViewState(
            password = "",
            email = ""
        )
    }
}
