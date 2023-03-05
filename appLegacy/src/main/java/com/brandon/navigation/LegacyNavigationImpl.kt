package com.brandon.navigation

import android.content.Context
import android.content.Intent
import com.brandon.applegacy.LandingScreen
import com.brandon.createaccountlegacy.view.CreateAccountActivity
import com.brandon.loginlegacy.view.LoginScreenActivity

class LegacyNavigationImpl(
    private val context: Context
) : LegacyNavigation {
    override fun navigateToLogin() {
        Intent(context, LoginScreenActivity::class.java).let { context.startActivity(it) }
    }

    override fun navigateToCreateAccount() {
        Intent(context, CreateAccountActivity::class.java).let { context.startActivity(it) }
    }

    override fun navigateToLandingPage() {
        Intent(context, LandingScreen::class.java).let { context.startActivity(it) }
    }
}