package com.brandon.appjava.navigation;

import android.content.Context;
import android.content.Intent;

import com.brandon.appjava.LandingPageActivity;
import com.brandon.corejava.navigation.LegacyNavigationJava;
import com.brandon.createaccountjava.view.CreateAccountActivity;
import com.brandon.loginmodulejava.view.LoginActivity;

import javax.inject.Inject;

public class LegacyNavigationImplJava implements LegacyNavigationJava {

    @Override
    public void navigateToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void navigateToCreateAccount(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void navigateToLandingPage(Context context) {
        Intent intent = new Intent(context, LandingPageActivity.class);
        context.startActivity(intent);
    }
}
