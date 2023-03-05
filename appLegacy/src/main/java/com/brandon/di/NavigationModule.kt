package com.brandon.di

import android.content.Context
import com.brandon.navigation.LegacyNavigation
import com.brandon.navigation.LegacyNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @Provides
    fun providesLegacyNavigation(@ActivityContext context: Context): LegacyNavigation {
        return LegacyNavigationImpl(context)
    }

}