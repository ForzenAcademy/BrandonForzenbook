package com.brandon.navigation

import android.content.Context
import android.content.Intent
import com.brandon.applegacy.LandingScreen
import com.brandon.createaccountlegacy.view.CreateAccountActivity
import com.brandon.loginlegacy.view.LoginScreenActivity

class LegacyNavigationImpl() : LegacyNavigation {
    override fun navigateToLogin(context: Context) {
        Intent(context, LoginScreenActivity::class.java).let { context.startActivity(it) }
    }

    override fun navigateToCreateAccount(context: Context) {
        Intent(context, CreateAccountActivity::class.java).let { context.startActivity(it) }
    }

    override fun navigateToLandingPage(context: Context) {
        Intent(context, LandingScreen::class.java).let { context.startActivity(it) }
    }
}