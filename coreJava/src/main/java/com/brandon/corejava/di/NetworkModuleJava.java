package com.brandon.corejava.di;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.room.Room;

import com.brandon.corejava.data.ForzenDaoJava;
import com.brandon.corejava.data.ForzenDatabaseJava;
import com.brandon.corejava.network.ForzenApiServiceJava;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(ViewModelComponent.class)
@Module
public class NetworkModuleJava {

    @Provides
    public Retrofit providesRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public ForzenApiServiceJava providesForzenApiService() {
        final String FORZEN_BASE_URL = "https://forzen.dev/api/";
        Retrofit retrofit = providesRetrofit(FORZEN_BASE_URL);
        return retrofit.create(ForzenApiServiceJava.class);
    }

    @Provides
    public ForzenDatabaseJava providesForzenDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, ForzenDatabaseJava.class, ForzenDatabaseJava.databaseName).build();
    }

    @Provides
    public ForzenDaoJava providesForzenDao(ForzenDatabaseJava forzenDatabaseJava) {
        return forzenDatabaseJava.forzenDaoJava();
    }

    @Provides
    public ConnectivityManager providesConnectivityManager(@ApplicationContext Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
