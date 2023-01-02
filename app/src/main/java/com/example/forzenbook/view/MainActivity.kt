package com.example.forzenbook.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.forzenbook.view.composables.CreateAccountContent
import com.example.forzenbook.view.composables.LoginContent
import com.example.forzenbook.view.composables.ResetPasswordContent
import com.example.forzenbook.view.navigation.LocalNavController
import com.example.forzenbook.view.navigation.NavigationDestinations
import com.example.forzenbook.view.theme.ForzenBookTheme
import com.example.forzenbook.viewmodels.ForzenTopLevelViewModel
import com.example.forzenbook.viewmodels.ManageAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val topLevelViewModel: ForzenTopLevelViewModel by viewModels()
    private val accountViewModel: ManageAccountViewModel by viewModels()

    // No Internet Error UI response (There is a class for this)
    // Bad Response Code Error

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
                            LoginContent(state = topLevelViewModel.state.value) { username, password ->
                                topLevelViewModel.login(username, password)
                            }
                        }
                        composable(NavigationDestinations.CREATE_ACCOUNT) {
                            CreateAccountContent(state = accountViewModel.state.value) { first, last, password, dob, email, location ->
                                accountViewModel.createUser(
                                    first,
                                    last,
                                    password,
                                    dob,
                                    email,
                                    location
                                )
                            }
                        }
                        composable(NavigationDestinations.FORGOT_PASSWORD) {
                            ResetPasswordContent(
                                state = accountViewModel.passwordState.value,
                                onDismiss = {
                                    navController.navigate(NavigationDestinations.LOGIN_INPUT) {
                                        popUpTo(NavigationDestinations.LOGIN_INPUT) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onSubmit = { accountViewModel.emailSent() },
                            )
                        }
                    }
                }
            }
        }
    }
}