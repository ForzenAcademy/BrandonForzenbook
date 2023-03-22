package com.brandon.appfragments.di

import com.brandon.appfragments.navigation.FragmentNavImpl
import com.brandon.navigation.FragmentNav
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object NavigationModule {
    @Provides
    fun providesNavImpl(): FragmentNav = FragmentNavImpl()
}