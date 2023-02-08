package com.brandon.logincore.di

import com.brandon.data.ForzenDao
import com.brandon.logincore.data.LoginRepository
import com.brandon.logincore.data.LoginRepositoryImpl
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.usecase.LoginUseCaseImpl
import com.brandon.network.ForzenApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object LoginModule {

    @Provides
    fun providesLoginRepo(
        apiService: ForzenApiService,
        forzenDao: ForzenDao
    ): LoginRepository {
        return LoginRepositoryImpl(apiService, forzenDao)
    }

    @Provides
    fun providesForzenLoginUseCase(
        repository: LoginRepository
    ): LoginUseCase {
        return LoginUseCaseImpl(repository)
    }

}