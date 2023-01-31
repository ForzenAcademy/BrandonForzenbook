package com.brandon.forzenbook.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandon.forzenbook.view.composables.CreateAccountContent
import com.brandon.forzenbook.view.composables.LoginContent
import com.brandon.forzenbook.view.navigation.LocalNavController
import com.brandon.forzenbook.view.navigation.NavigationDestinations
import com.brandon.forzenbook.view.theme.ForzenBookTheme
import com.brandon.forzenbook.viewmodels.CreateAccountViewModel
import com.brandon.forzenbook.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val createViewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForzenBookTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationDestinations.LOGIN_INPUT
                    ) {
                        composable(NavigationDestinations.LOGIN_INPUT) {
                            LoginContent(
                                state = loginViewModel.uiState.value,
                                onGetCode = { loginViewModel.loginClicked(it) },
                                onLogin = { email, code ->
                                    loginViewModel.loginClicked(email, code)
                                },
                                onCheckConnection = { loginViewModel.checkInternetConnection() },
                            )
                        }
                        composable(NavigationDestinations.CREATE_ACCOUNT) {
                            CreateAccountContent(
                                state = createViewModel.createAccountState.value,
                                onSubmit = { firstName, lastName, dateOfBirth, email, location ->
                                    createViewModel.createAccountClicked(
                                        firstName = firstName,
                                        lastName = lastName,
                                        date = dateOfBirth,
                                        email = email,
                                        location = location,
                                    )
                                },
                                onCheckConnection = { loginViewModel.checkInternetConnection() }
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val VIEW_LOG_TAG = "Brandon_Test_View"
        const val KEYBOARD_ERROR = "Error Hiding Keyboard"
    }
}