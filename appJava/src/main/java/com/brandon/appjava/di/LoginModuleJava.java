package com.brandon.appjava.di;

import android.net.ConnectivityManager;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.brandon.appjava.data.ForzenDaoJava;
import com.brandon.appjava.data.login.LoginRepoImplJava;
import com.brandon.appjava.data.login.LoginRepoJava;
import com.brandon.appjava.domain.login.LoginUseCaseImplJava;
import com.brandon.appjava.domain.login.LoginUseCaseJava;
import com.brandon.appjava.network.ForzenApiServiceJava;
import com.brandon.appjava.viewmodel.MyViewModelFactory;
import com.brandon.appjava.viewmodel.login.LegacyLoginViewModel;
import com.brandon.appjava.viewmodel.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap;
import dagger.multibindings.IntoMap;

@InstallIn({ViewModelComponent.class, ActivityComponent.class})
@Module
public abstract class LoginModuleJava {

    @Provides
    public static LoginRepoJava providesLoginRepo(ForzenApiServiceJava forzenApiServiceJava, ForzenDaoJava forzenDaoJava) {
        return new LoginRepoImplJava(forzenApiServiceJava, forzenDaoJava);
    }

    @Provides
    public static LoginUseCaseJava providesLoginUseCase(LoginRepoJava loginRepoJava) {
        return new LoginUseCaseImplJava(loginRepoJava);
    }

    @Binds
    public abstract ViewModelProvider.Factory bindMyViewModelFactory(MyViewModelFactory factory);

    @Provides
    public static LegacyLoginViewModel provideLoginViewModel(LoginUseCaseJava loginUseCaseJava, ConnectivityManager connectivityManager) {
        return new LegacyLoginViewModel(loginUseCaseJava, connectivityManager);
    }
}
