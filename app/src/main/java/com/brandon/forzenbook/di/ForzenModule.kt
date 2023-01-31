package com.brandon.forzenbook.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.brandon.forzenbook.data.ForzenDao
import com.brandon.forzenbook.data.ForzenDatabase
import com.brandon.forzenbook.network.ForzenApiService
import com.brandon.forzenbook.network.ForzenApiService.Companion.FORZEN_BASE_URL
import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.ForzenRepositoryImpl
import com.brandon.forzenbook.usecase.*
import com.google.gson.GsonBuilder
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
        val gson = GsonConverterFactory.create(GsonBuilder().setLenient().create())
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gson)
            .build()
    }

    @Provides
    fun providesForzenApiService(): ForzenApiService {
        val retrofit = providesRetrofit(FORZEN_BASE_URL)
        return retrofit.create(ForzenApiService::class.java)
    }

    @Provides
    fun providesForzenDatabase(@ApplicationContext context: Context): ForzenDatabase {
        return Room.databaseBuilder(context, ForzenDatabase::class.java, ForzenDatabase.NAME)
            .build()
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

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun ConnectivityManager.isConnected(): Boolean = activeNetworkInfo?.isConnected() ?: false

}