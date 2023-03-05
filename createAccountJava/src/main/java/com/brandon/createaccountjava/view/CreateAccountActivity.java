package com.brandon.createaccountjava.view;


import static com.brandon.corejava.utilities.CommonActivityFunctions.onTextChanged;
import static com.brandon.corejava.utilities.CommonActivityFunctions.setFieldVisibility;
import static com.brandon.createaccountjava.viewmodel.CreateAccountUiStates.ERROR;
import static com.brandon.createaccountjava.viewmodel.CreateAccountUiStates.LOADING;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brandon.corejava.utilities.CustomDatePicker;
import com.brandon.corejava.utilities.NotificationDialog;
import com.brandon.createaccountjava.databinding.CreateAccountScreenBinding;
import com.brandon.createaccountjava.viewmodel.CreateAccountUiStates;
import com.brandon.createaccountjava.viewmodel.LegacyCreateAccountViewModel;
import com.brandon.uicore.R;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class CreateAccountActivity extends AppCompatActivity {
    private LegacyCreateAccountViewModel viewModel;
    private Disposable disposable;
    private CreateAccountScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LegacyCreateAccountViewModel.class);
        binding = CreateAccountScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final TextInputEditText firstNameField = binding.firstNameField;
        final TextInputEditText lastNameField = binding.lastNameField;
        final TextInputEditText emailField = binding.emailField;
        final TextInputEditText dateField = binding.dateField;
        final TextInputEditText locationField = binding.locationField;
        final TextView firstError = binding.firstNameError;
        final TextView lastError = binding.lastNameError;
        final TextView emailError = binding.emailError;
        final TextView dateError = binding.dateError;
        final TextView locationError = binding.locationError;

        onTextChanged(firstNameField, (String s) -> viewModel.firstNameChanged(s));
        onTextChanged(lastNameField, (String s) -> viewModel.lastNameChanged(s));
        onTextChanged(emailField, (String s) -> viewModel.emailChanged(s));
        onTextChanged(dateField, (String s) -> viewModel.dateChanged(s));
        onTextChanged(locationField, (String s) -> viewModel.locationChanged(s));

        dateField.setOnClickListener(v -> new CustomDatePicker().openDialog(
                this,
                () -> viewModel.datePickerOpened(true),
                (date) -> {
                    dateField.setText((date.get(1) + 1) + "/" + date.get(2) + "/" + date.get(0));
                    viewModel.datePickerOpened(false);
                },
                () -> viewModel.datePickerOpened(false)
        ));


        if (binding.backArrow != null) {
            binding.backArrow.setOnClickListener(v -> viewModel.navigateToLogin(this));
        }

        binding.createButton.setOnClickListener(v -> {
            if (binding.backArrow != null) {
                if (binding.fieldsError != null) {
                    binding.fieldsError.setVisibility(View.GONE);
                }
            }
            firstNameField.clearFocus();
            firstNameField.setSelected(false);
            lastNameField.clearFocus();
            lastNameField.setSelected(false);
            emailField.clearFocus();
            emailField.setSelected(false);
            dateField.clearFocus();
            dateField.setSelected(false);
            locationField.clearFocus();
            locationField.setSelected(false);
            final String first = firstNameField.getText() != null ? firstNameField.getText().toString() : "";
            final String last = lastNameField.getText() != null ? lastNameField.getText().toString() : "";
            final String email = emailField.getText() != null ? emailField.getText().toString() : "";
            final String date = dateField.getText() != null ? dateField.getText().toString() : "";
            final String location = locationField.getText() != null ? locationField.getText().toString() : "";
            if (!first.isEmpty() && !last.isEmpty() && !email.isEmpty() && !date.isEmpty() && !location.isEmpty()) {
                viewModel.createAccountClicked(first, last, email, date, location);
            } else {
                binding.fieldsError.setVisibility(View.VISIBLE);
            }
        });

        disposable = viewModel.getState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    CreateAccountUiStates state = it.getState();
                    if (it.isSuccess()) {
                        new NotificationDialog().createNotification(
                                this, getString(R.string.create_account_duplicate_user_error),
                                getString(R.string.create_account_to_login),
                                () -> {
                                    viewModel.navigateToLogin(this);
                                    viewModel.notificationDialogOpened(false);
                                },
                                () -> viewModel.notificationDialogOpened(true)
                        );
                    } else if (state == ERROR) {
                        new NotificationDialog().createNotification(
                                this, getString(R.string.create_account_duplicate_user_error),
                                getString(R.string.create_account_to_login),
                                () -> {
                                    viewModel.navigateToLogin(this);
                                    viewModel.notificationDialogOpened(false);
                                },
                                () -> viewModel.notificationDialogOpened(true)
                        );
                    } else if (state == LOADING) {
                        binding.progressSpinner.setVisibility(View.GONE);
                        binding.buttonText.setVisibility(View.VISIBLE);
                        firstNameField.setText(it.getFirstName());
                        firstNameField.setEnabled(false);
                        lastNameField.setText(it.getLastName());
                        lastNameField.setEnabled(false);
                        emailField.setText(it.getEmail());
                        emailField.setEnabled(false);
                        dateField.setText(it.getDate());
                        dateField.setEnabled(false);
                        locationField.setText(it.getLocation());
                        locationField.setEnabled(false);
                        setFieldVisibility(firstError, it.getFirstNameError());
                        setFieldVisibility(lastError, it.getLastNameError());
                        setFieldVisibility(emailError, it.getEmailError());
                        setFieldVisibility(dateError, it.getDateError());
                        setFieldVisibility(locationError, it.getLocationError());
                    } else {
                        binding.progressSpinner.setVisibility(View.GONE);
                        binding.buttonText.setVisibility(View.VISIBLE);
                        firstNameField.setText(it.getFirstName());
                        firstNameField.setEnabled(true);
                        lastNameField.setText(it.getLastName());
                        lastNameField.setEnabled(true);
                        emailField.setText(it.getEmail());
                        emailField.setEnabled(true);
                        dateField.setText(it.getDate());
                        dateField.setEnabled(true);
                        locationField.setText(it.getLocation());
                        locationField.setEnabled(true);
                        setFieldVisibility(firstError, it.getFirstNameError());
                        setFieldVisibility(lastError, it.getLastNameError());
                        setFieldVisibility(emailError, it.getEmailError());
                        setFieldVisibility(dateError, it.getDateError());
                        setFieldVisibility(locationError, it.getLocationError());
                        if (it.isDateDialogOpen()) {
                            new CustomDatePicker().openDialog(
                                    this,
                                    () -> viewModel.datePickerOpened(true),
                                    (date) -> {
                                        dateField.setText((date.get(1) + 1) + "/" + date.get(2) + "/" + date.get(0));
                                        viewModel.datePickerOpened(false);
                                    },
                                    () -> viewModel.datePickerOpened(false)
                            );
                        }
                        if (it.isConnectionDialogOpen()) {
                            new NotificationDialog().createNotification(
                                    this, getString(R.string.core_error_no_internet_connection),
                                    getString(R.string.core_error_connect_and_try_again),
                                    () -> viewModel.notificationDialogOpened(false),
                                    () -> viewModel.notificationDialogOpened(true)
                            );
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}