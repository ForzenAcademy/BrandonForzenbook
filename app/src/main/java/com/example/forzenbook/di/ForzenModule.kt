package com.example.forzenbook.di

import android.content.Context
import androidx.room.Room
import com.example.forzenbook.data.ForzenDao
import com.example.forzenbook.data.ForzenDatabase
import com.example.forzenbook.network.ForzenApiService
import com.example.forzenbook.network.ForzenApiService.Companion.FORZEN_BASE_URL
import com.example.forzenbook.network.mocks.ForzenApiServiceAlwaysSuccessMock
import com.example.forzenbook.repository.ForzenRepository
import com.example.forzenbook.repository.ForzenRepositoryImpl
import com.example.forzenbook.usecase.CreateUserUseCase
import com.example.forzenbook.usecase.CreateUserUseCaseImpl
import com.example.forzenbook.usecase.LoginUseCase
import com.example.forzenbook.usecase.LoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ViewModelComponent::class)
@Module
object ForzenModule {

    @Provides
    fun providesRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesForzenApiService(): ForzenApiService {
        val retrofit = providesRetrofit(FORZEN_BASE_URL)
//        return retrofit.create(ForzenApiService::class.java)
        return ForzenApiServiceAlwaysSuccessMock()
    }

    @Provides
    fun providesForzenDatabase(@ApplicationContext context: Context): ForzenDatabase {
        return Room.databaseBuilder(context, ForzenDatabase::class.java, ForzenDatabase.NAME).build()
    }

    @Provides
    fun providesForzenDao(forzenDatabase: ForzenDatabase): ForzenDao {
        return forzenDatabase.forzenDao()
    }

    @Provides
    fun providesForzenRepo(apiService: ForzenApiService, forzenDao: ForzenDao): ForzenRepository {
        return ForzenRepositoryImpl(apiService, forzenDao)
    }

    @Provides
    fun providesForzenLoginUseCase(
        repository: ForzenRepository
    ): LoginUseCase {
        return LoginUseCaseImpl(repository)
    }

    @Provides
    fun providesForzenCreateUserCase(
        repository: ForzenRepository
    ): CreateUserUseCase {
        return CreateUserUseCaseImpl(repository)
    }

}