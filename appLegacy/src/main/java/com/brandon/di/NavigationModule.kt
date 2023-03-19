package com.brandon.di

import com.brandon.navigation.LegacyNavigation
import com.brandon.navigation.LegacyNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class NavigationModule {

    @Provides
    fun providesLegacyNavigation(): LegacyNavigation {
        return LegacyNavigationImpl()
    }

}