package com.brandon.createaccountfragment.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel
import com.brandon.createaccountfragment.databinding.CreateAccountScreenBinding
import com.brandon.createaccountfragment.viewmodel.FragmentCreateAccountViewModel
import com.brandon.fragmentcore.DatePickerDialogFragment
import com.brandon.fragmentcore.NotificationDialogFragment
import com.brandon.uicore.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private var binding: CreateAccountScreenBinding? = null
    private val viewModel: FragmentCreateAccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CreateAccountScreenBinding.inflate(layoutInflater).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewModelScope.launch {
            viewModel.uiState.collect {
                updateState(it)
                Log.e(CreateAccountViewModel.VIEWMODEL_TAG, it.toString())
            }
        }

        binding?.apply {
            backArrow.setOnClickListener {
                // TODO FA-125 Add Navigation Between Fragments
            }
            dateField.setOnClickListener {
                DatePickerDialogFragment(
                    onDatePicked = { year, month, dayOfMonth ->
                        dateField.setText("${month + 1}/$dayOfMonth/$year")
                    }
                ).show(childFragmentManager, null)
            }
            createButton.setOnClickListener {
                fieldsError.isVisible = false
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
                } else fieldsError.isVisible = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun updateState(uiState: CreateAccountViewModel.CreateAccountUiState) {
        binding?.apply {
            when (uiState) {
                is CreateAccountViewModel.CreateAccountUiState.Idle -> {
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
                is CreateAccountViewModel.CreateAccountUiState.Loading -> {
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
                    ).forEach { it.isVisible = false }
                }
                is CreateAccountViewModel.CreateAccountUiState.Loaded -> {
                    NotificationDialogFragment(
                        title = getString(R.string.core_success),
                        body = getString(R.string.create_account_success_body),
                        onDismiss = {
                            // TODO FA-125 Add Navigation Between Fragments
                        }).show(childFragmentManager, null)
                }
            }
        }
    }
}