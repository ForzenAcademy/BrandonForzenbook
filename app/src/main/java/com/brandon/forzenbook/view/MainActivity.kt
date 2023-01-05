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
import com.brandon.forzenbook.view.composables.ResetPasswordContent
import com.brandon.forzenbook.view.navigation.LocalNavController
import com.brandon.forzenbook.view.navigation.NavigationDestinations
import com.brandon.forzenbook.view.theme.DpProvider
import com.brandon.forzenbook.view.theme.ForzenBookTheme
import com.brandon.forzenbook.view.theme.LocalSizeController
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val topLevelViewModel: ForzenTopLevelViewModel by viewModels()
    private val accountViewModel: ManageAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForzenBookTheme {
                val sizes = DpProvider()
                CompositionLocalProvider(LocalSizeController provides sizes) {
                    val navController = rememberNavController()
                    CompositionLocalProvider(LocalNavController provides navController) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationDestinations.LOGIN_INPUT
                        ) {
                            composable(NavigationDestinations.LOGIN_INPUT) {
                                LoginContent(
                                    state = topLevelViewModel.state.value,
                                    onDismiss = {
                                        topLevelViewModel.dismissNotification()
                                    },
                                    onSubmit = { username, password ->
                                        topLevelViewModel.login(username, password)
                                    }
                                )
                            }
                            composable(NavigationDestinations.CREATE_ACCOUNT) {
                                CreateAccountContent(
                                    state = accountViewModel.state.value,
                                    onDismiss = {
                                        accountViewModel.dismissErrorNotification()
                                    },
                                    onSubmit = { firstName, lastName, password, dateOfBirth, email, location ->
                                        accountViewModel.createUser(
                                            firstName = firstName,
                                            lastName = lastName,
                                            password = password,
                                            date = dateOfBirth,
                                            email = email,
                                            location = location,
                                        )
                                    }
                                )
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
}