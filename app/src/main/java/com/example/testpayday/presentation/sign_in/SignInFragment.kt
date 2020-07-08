package com.example.testpayday.presentation.sign_in

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.testpayday.R
import com.example.testpayday.core.base.presentation.BaseFragment
import com.example.testpayday.core.base.presentation.ViewModelFactory
import com.example.testpayday.core.base.presentation.viewmodel.ErrorMessageEvent
import com.example.testpayday.core.base.presentation.viewmodel.Event
import com.example.testpayday.core.library.edittext.TextChangeListener
import com.example.testpayday.core.library.livedata.observe
import com.example.testpayday.core.library.viewmodel.bindViewModel
import com.example.testpayday.core.library.viewmodel.kodeinViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.*

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    override val kodeinModule = Kodein.Module(SignInFragment::class.java.simpleName) {
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bindViewModel<SignInViewModel>() with provider {
            SignInViewModel(
                router = instance(),
                authInteractor = instance()
            )
        }
    }

    private val viewModel: SignInViewModel by kodeinViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        observe(viewModel.eventsQueue, ::showToastMessage)
    }

    private fun initViews() {
        editTextTextEmailAddress.addTextChangedListener(TextChangeListener(viewModel::onEmailChanged))
        editTextTextPassword.addTextChangedListener(TextChangeListener(viewModel::onPasswordChanged))
        btnContinue.setOnClickListener { viewModel.onContinueClicked() }
        btnSignUp.setOnClickListener { viewModel.onSignUpClicked() }
    }

    private fun showToastMessage(data: Queue<Event>) {
        data.firstOrNull()?.takeIf {
            it is ErrorMessageEvent
        }?.let {
            it as ErrorMessageEvent

            Toast.makeText(
                requireActivity(),
                it.errorMessageResource.toString(requireActivity()),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
