package com.brandon.appjava.view.navigation;

import android.content.Context;
import android.content.Intent;

import com.brandon.appjava.view.CreateAccountActivity;
import com.brandon.appjava.view.LandingPageActivity;
import com.brandon.appjava.view.LoginActivity;

import javax.inject.Inject;

public class LegacyNavigationImplJava implements LegacyNavigationJava {
    private final Context context;

    @Inject
    public LegacyNavigationImplJava(Context context) {
        this.context = context;
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void navigateToCreateAccount() {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void navigateToLandingPage() {
        Intent intent = new Intent(context, LandingPageActivity.class);
        context.startActivity(intent);
    }
}
