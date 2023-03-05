package com.brandon.loginmodulejava.view;

import static com.brandon.corejava.utilities.CommonActivityFunctions.onTextChanged;
import static com.brandon.loginmodulejava.viewmodel.LoginUiStates.LOADING;
import static com.brandon.loginmodulejava.viewmodel.LoginUiStates.LOGGED_IN;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brandon.corejava.utilities.NotificationDialog;
import com.brandon.loginmodulejava.databinding.LoginScreenBinding;
import com.brandon.loginmodulejava.viewmodel.LegacyLoginViewModel;
import com.brandon.loginmodulejava.viewmodel.LoginUiStates;
import com.brandon.uicore.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private LegacyLoginViewModel viewModel;
    private Disposable uiStateSubscription;
    private LoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginScreenBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(LegacyLoginViewModel.class);
        setContentView(binding.getRoot());

        final TextInputEditText emailField = binding.loginEmailField;
        final TextInputEditText codeField = binding.loginCodeField;

        onTextChanged(emailField, (String s) -> viewModel.emailChanged(s));

        binding.loginButton.setOnClickListener(v -> {
            final TextView fieldsError = binding.loginRequiredFieldsError;
            fieldsError.setVisibility(View.GONE);
            String email = emailField.getText() != null ? emailField.getText().toString() : "";
            String code = codeField.getText() != null ? codeField.getText().toString() : "";
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
                new NotificationDialog().createNotification(
                        this, getString(R.string.core_error_no_internet_connection),
                        getString(R.string.core_error_connect_and_try_again),
                        () -> viewModel.notificationDialogOpened(false),
                        () -> viewModel.notificationDialogOpened(true)
                );
            }
        });

        binding.createAccountRedirect.setOnClickListener(v -> {
            // TODO FA-117 Navigate to Create Account
        });

        uiStateSubscription = viewModel.getState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    LoginUiStates state = it.getState();
                    if (state == LOGGED_IN) {
                        // TODO FA - 117 Navigate to Landing Page
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
        if (uiStateSubscription != null && !uiStateSubscription.isDisposed()) {
            uiStateSubscription.dispose();
        }
    }
}