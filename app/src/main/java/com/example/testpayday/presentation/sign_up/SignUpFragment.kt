package com.example.testpayday.presentation.sign_up

import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.joda.time.DateTime
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.*

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    override val kodeinModule = Kodein.Module(SignUpFragment::class.java.simpleName) {
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bindViewModel<SignUpViewModel>() with provider {
            SignUpViewModel(
                router = instance(),
                authInteractor = instance()
            )
        }
    }

    private val viewModel: SignUpViewModel by kodeinViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        observe(viewModel.eventsQueue, ::showToastMessage)
        observe(viewModel.dobText, ::renderDobText)
    }

    private fun initViews() {
        etEmail.addTextChangedListener(TextChangeListener(viewModel::onEmailChanged))
        etPassword.addTextChangedListener(TextChangeListener(viewModel::onPasswordChanged))
        etFirstName.addTextChangedListener(TextChangeListener(viewModel::onFirstNameChanged))
        etLastName.addTextChangedListener(TextChangeListener(viewModel::onLastNameChanged))
        etGender.addTextChangedListener(TextChangeListener(viewModel::onGenderChanged))
        etPhone.addTextChangedListener(TextChangeListener(viewModel::onPhoneChanged))

        button.setOnClickListener {
            viewModel.onContinueClicked()
        }

        dateClickZone.setOnClickListener {
            val now = DateTime.now()
            DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    DateTime(year, month.inc(), dayOfMonth, 0, 0).let {
                        viewModel.onDobChanged(it)
                    }
                },
                now.year,
                now.monthOfYear,
                now.dayOfMonth
            ).show()
        }
    }

    private fun renderDobText(text: String) {
        etDob.setText(text)
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
