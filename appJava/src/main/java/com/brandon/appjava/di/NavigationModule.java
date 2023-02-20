package com.brandon.appjava.di;

import android.content.Context;

import com.brandon.appjava.view.navigation.LegacyNavigationImplJava;
import com.brandon.appjava.view.navigation.LegacyNavigationJava;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class NavigationModule {

    @Provides
    public LegacyNavigationJava providesNavigation(@ActivityContext Context context) {
        return new LegacyNavigationImplJava(context);
    }

}
