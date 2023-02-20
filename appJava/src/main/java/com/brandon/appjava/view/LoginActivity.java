package com.brandon.appjava.view;

import static com.brandon.appjava.viewmodel.login.LoginUiStates.LOADING;
import static com.brandon.appjava.viewmodel.login.LoginUiStates.LOGGED_IN;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brandon.appjava.databinding.LoginScreenBinding;
import com.brandon.appjava.utilities.CommonActivityFunctions;
import com.brandon.appjava.utilities.NotificationDialog;
import com.brandon.appjava.view.navigation.LegacyNavigationJava;
import com.brandon.appjava.viewmodel.login.LegacyLoginViewModel;
import com.brandon.appjava.viewmodel.login.LoginUiStates;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity implements CommonActivityFunctions {
    @Inject
    LegacyLoginViewModel viewModel;
    private Disposable disposable;
    private LoginScreenBinding binding;
    @Inject
    LegacyNavigationJava navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final TextInputEditText emailField = binding.loginEmailField;
        final TextInputEditText codeField = binding.loginCodeField;

        onTextChanged(binding.loginEmailField, (String s) -> viewModel.emailChanged(s));

        binding.loginButton.setOnClickListener(v -> {
            final TextView fieldsError = binding.loginRequiredFieldsError;
            fieldsError.setVisibility(View.GONE);
            String email = Objects.requireNonNull(emailField.getText()).toString();
            String code = Objects.requireNonNull(codeField.getText()).toString();
            if (viewModel.checkInternetConnection()) {
                if (!email.isEmpty()) {
                    if (!code.isEmpty()) {
                        viewModel.loginClicked(email, code);
                    } else {
                        viewModel.loginClicked(email, null);
                    }
                } else {
                    fieldsError.setVisibility(View.VISIBLE);
                }
            } else {
                new NotificationDialog().createNotification(this, getString(com.brandon.uicore.R.string.core_error_no_internet_connection), getString(com.brandon.uicore.R.string.core_error_connect_and_try_again), null);
            }
        });

        binding.createAccountRedirect.setOnClickListener(v -> {
            navigation.navigateToCreateAccount();
        });

        disposable = viewModel.getState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    LoginUiStates state = it.getState();
                    if (state == LOGGED_IN) {
                        navigation.navigateToLandingPage();
                    } else if (state == LOADING) {
                        binding.buttonText.setVisibility(View.GONE);
                        binding.progressSpinner.setVisibility(View.VISIBLE);
                        if (it.isCodeSent()) {
                            codeField.setVisibility(View.VISIBLE);
                        } else {
                            codeField.setVisibility(View.GONE);
                        }
                        binding.loginButton.setEnabled(false);
                        emailField.setText(it.getEmail());
                        emailField.setEnabled(false);
                        codeField.setEnabled(false);
                    } else {
                        binding.progressSpinner.setVisibility(View.GONE);
                        binding.buttonText.setVisibility(View.VISIBLE);
                        if (it.isCodeSent()) {
                            codeField.setVisibility(View.VISIBLE);
                            codeField.setEnabled(true);
                        } else {
                            codeField.setVisibility(View.GONE);
                            codeField.setEnabled(false);
                        }
                        binding.loginButton.setEnabled(true);
                        emailField.setEnabled(true);
                        emailField.setText(it.getEmail());
                        if (it.isEmailError()) {
                            binding.loginEmailError.setVisibility(View.VISIBLE);
                        } else {
                            binding.loginEmailError.setVisibility(View.GONE);
                        }
                        if (it.isCodeSent()) {
                            binding.buttonText.setText(com.brandon.uicore.R.string.core_get_code_text);
                        } else {
                            binding.buttonText.setText(com.brandon.uicore.R.string.login_button_text);
                        }
                        if (it.isGenericError()) {
                            new NotificationDialog().createNotification(this, getString(com.brandon.uicore.R.string.core_error_no_internet_connection), getString(com.brandon.uicore.R.string.core_error_connect_and_try_again), null);
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