package com.brandon.forzenbook.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandon.composecore.navigation.LocalNavController
import com.brandon.composecore.navigation.NavDestinations.CREATE_ACCOUNT
import com.brandon.composecore.navigation.NavDestinations.CREATE_POST_SCREEN
import com.brandon.composecore.navigation.NavDestinations.FEED_SCREEN
import com.brandon.composecore.navigation.NavDestinations.LOGIN_INPUT
import com.brandon.composecore.theme.ForzenBookTheme
import com.brandon.createaccount.compose.CreateAccountContent
import com.brandon.createaccount.viewmodel.ComposeCreateAccountViewModel
import com.brandon.feedcompose.CreatePostScreen
import com.brandon.feedcompose.FeedScreen
import com.brandon.logincompose.view.LoginContent
import com.brandon.logincompose.viewmodel.ComposeLoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: ComposeLoginViewModel by viewModels()
    private val createViewModel: ComposeCreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForzenBookTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(
                        navController = navController,
                        startDestination = LOGIN_INPUT
                    ) {
                        composable(LOGIN_INPUT) {
                            LoginContent(
                                state = loginViewModel.uiState.value,
                                onGetCode = { loginViewModel.loginClicked(it) },
                                onLogin = { email, code ->
                                    loginViewModel.loginClicked(email, code)
                                },
                                onCheckConnection = { loginViewModel.checkInternetConnection() },
                                onLoggedIn = {
                                    navController.navigate(FEED_SCREEN) {
                                        popUpTo(FEED_SCREEN) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(CREATE_ACCOUNT) {
                            CreateAccountContent(
                                state = createViewModel.uiState.value,
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
                        composable(FEED_SCREEN) {
                            FeedScreen(
                                onCreateFeedClicked = {
                                    navController.navigate(CREATE_POST_SCREEN) {
                                        popUpTo(FEED_SCREEN) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(CREATE_POST_SCREEN) {
                            CreatePostScreen()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val VIEW_LOG_TAG = "Brandon_Test_View"
    }
}