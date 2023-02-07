package com.brandon.createaccount.core.di

import com.brandon.createaccount.core.data.CreateUserRepository
import com.brandon.createaccount.core.data.CreateUserRepositoryImpl
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.createaccount.core.usecase.CreateUserUseCaseImpl
import com.brandon.network.ForzenApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object CreateAccountModule {

    @Provides
    fun providesCreateUserRepo(
        apiService: ForzenApiService,
    ): CreateUserRepository {
        return CreateUserRepositoryImpl(apiService)
    }

    @Provides
    fun providesForzenCreateUserCase(
        repository: CreateUserRepository
    ): CreateUserUseCase {
        return CreateUserUseCaseImpl(repository)
    }

}