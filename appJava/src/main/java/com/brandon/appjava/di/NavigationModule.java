package com.brandon.appjava.di;

import com.brandon.appjava.navigation.LegacyNavigationImplJava;
import com.brandon.corejava.navigation.LegacyNavigationJava;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class NavigationModule {

    @Provides
    public LegacyNavigationJava providesNavigation() {
        return new LegacyNavigationImplJava();
    }

}
