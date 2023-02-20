package com.brandon.appjava.view;

import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.DUPLICATE;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.LOADING;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.SUCCESS;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brandon.appjava.databinding.CreateAccountScreenBinding;
import com.brandon.appjava.utilities.CommonActivityFunctions;
import com.brandon.appjava.utilities.NotificationDialog;
import com.brandon.appjava.view.navigation.LegacyNavigationJava;
import com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates;
import com.brandon.appjava.viewmodel.createaccount.LegacyCreateAccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class CreateAccountActivity extends AppCompatActivity implements CommonActivityFunctions {
    @Inject
    LegacyCreateAccountViewModel viewModel;
    @Inject
    LegacyNavigationJava navigation;
    private Disposable disposable;
    private CreateAccountScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        dateField.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    com.brandon.uicore.R.style.MySpinnerStyle,
                    null,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                dateField.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                dialog.hide();
            });
            dialog.setOnDismissListener(view -> dialog.hide());
            dialog.show();
        });

        Objects.requireNonNull(binding.backArrow).setOnClickListener(v -> navigation.navigateToLogin());

        binding.createButton.setOnClickListener(v -> {
            Objects.requireNonNull(binding.fieldsError).setVisibility(View.GONE);
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
            final String first = Objects.requireNonNull(firstNameField.getText()).toString();
            final String last = Objects.requireNonNull(lastNameField.getText()).toString();
            final String email = Objects.requireNonNull(emailField.getText()).toString();
            final String date = Objects.requireNonNull(dateField.getText()).toString();
            final String location = Objects.requireNonNull(locationField.getText()).toString();
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
                    if (state == SUCCESS) {
                        new NotificationDialog().createNotification(this, getString(com.brandon.uicore.R.string.core_success), getString(com.brandon.uicore.R.string.create_account_to_login), () -> navigation.navigateToLogin());
                    } else if (state == DUPLICATE) {
                        new NotificationDialog().createNotification(this, getString(com.brandon.uicore.R.string.create_account_duplicate_user_error), getString(com.brandon.uicore.R.string.create_account_to_login), () -> navigation.navigateToLogin());
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