package com.brandon.navigation

import androidx.fragment.app.FragmentManager

interface FragmentNav {
    fun navigateToCreateAccount(fragmentManager: FragmentManager)
    fun navigateToLogin(fragmentManager: FragmentManager)
    fun navigateToLandingPage(fragmentManager: FragmentManager)
}