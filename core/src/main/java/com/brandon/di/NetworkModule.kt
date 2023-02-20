package com.brandon.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.brandon.data.ForzenDao
import com.brandon.data.ForzenDatabase
import com.brandon.network.ForzenApiService
import com.brandon.network.ForzenApiService.Companion.FORZEN_BASE_URL
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
object NetworkModule {

    @Provides
    fun providesRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
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
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}