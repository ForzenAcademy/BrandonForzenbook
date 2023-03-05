package com.brandon.createaccountlegacy.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.Companion.VIEWMODEL_TAG
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.CreateAccountUiState
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.CreateAccountUiState.*
import com.brandon.createaccountlegacy.databinding.CreateAccountScreenBinding
import com.brandon.createaccountlegacy.viewmodel.LegacyCreateAccountViewModel
import com.brandon.navigation.LegacyNavigation
import com.brandon.uicore.R
import com.brandon.uicore.connectionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateAccountActivity : AppCompatActivity() {

    private val viewModel: LegacyCreateAccountViewModel by viewModels()
    private lateinit var binding: CreateAccountScreenBinding
    @Inject
    lateinit var legacyNavigation: LegacyNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateAccountScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.viewModelScope.launch {
            viewModel.uiState.collect {
                updateState(it)
                Log.e(VIEWMODEL_TAG, it.toString())
            }
        }

        binding.apply {
            backArrow?.setOnClickListener {
                legacyNavigation.navigateToLogin()
            }
            dateField.setOnClickListener {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    this@CreateAccountActivity,
                    R.style.MySpinnerStyle,
                    null,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    setOnDateSetListener { _, year, month, dayOfMonth ->
                        dateField.setText("${month + 1}/$dayOfMonth/$year")
                        hide()
                    }
                    setOnDismissListener { hide() }
                }.show()
            }
            createButton.setOnClickListener {
                fieldsError?.isVisible = false
                val fields = listOf(
                    firstNameField,
                    lastNameField,
                    dateField,
                    emailField,
                    locationField
                )
                fields.forEach {
                    it.clearFocus()
                    it.isSelected = false
                }
                val textList = fields.map { it.text.toString() }
                if (textList.all { it.isNotEmpty() }) {
                    viewModel.createAccountClicked(
                        textList[0],
                        textList[1],
                        textList[2],
                        textList[3],
                        textList[4]
                    )
                } else fieldsError?.isVisible = true
            }
        }
    }

    private fun updateState(uiState: CreateAccountUiState) {
        binding.apply {
            when (uiState) {
                is Idle -> {
                    progressSpinner.isVisible = false
                    buttonText.isVisible = true
                    firstNameField.setText(uiState.firstName)
                    lastNameField.setText(uiState.lastName)
                    emailField.setText(uiState.email)
                    dateField.setText(uiState.dateOfBirth)
                    locationField.setText(uiState.location)
                    firstNameError.isVisible = uiState.isFirstNameError
                    lastNameError.isVisible = uiState.isLastNameError
                    emailError.isVisible = uiState.isEmailError
                    dateError.isVisible = uiState.isDateError
                    locationError.isVisible = uiState.isLocationError
                }
                is Loading -> {
                    progressSpinner.isVisible = true
                    buttonText.isVisible = false
                    firstNameField.setText(uiState.firstName)
                    lastNameField.setText(uiState.lastName)
                    emailField.setText(uiState.email)
                    dateField.setText(uiState.dateOfBirth)
                    locationField.setText(uiState.location)
                    listOf(
                        firstNameError,
                        lastNameError,
                        dateError,
                        emailError,
                        locationError,
                        fieldsError
                    ).forEach { it?.isVisible = false }
                }
                is Loaded -> {
                    connectionDialog(
                        this@CreateAccountActivity,
                        titleText = getString(R.string.core_success),
                        bodyText = getString(R.string.create_account_success_body)
                    ) {
                        legacyNavigation.navigateToLogin()
                    }
                }
            }
        }
    }
}