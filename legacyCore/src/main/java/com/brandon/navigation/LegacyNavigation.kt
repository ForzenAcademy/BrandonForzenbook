package com.brandon.navigation

import android.content.Context

interface LegacyNavigation {
    fun navigateToLogin(context: Context)
    fun navigateToCreateAccount(context: Context)
    fun navigateToLandingPage(context: Context)
}