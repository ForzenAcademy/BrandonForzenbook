package com.brandon.forzenbook.di

import android.content.Context
import android.net.ConnectivityManager
import com.brandon.forzenbook.repository.CreateUserRepository
import com.brandon.forzenbook.repository.CreateUserRepositoryImpl
import com.brandon.forzenbook.usecase.*
import com.brandon.network.ForzenApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
object ForzenModule {

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

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun ConnectivityManager.isConnected(): Boolean = activeNetworkInfo?.isConnected() ?: false

}