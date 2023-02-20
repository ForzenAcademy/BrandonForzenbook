package com.brandon.appjava.di;

import android.net.ConnectivityManager;

import androidx.lifecycle.ViewModelProvider;

import com.brandon.appjava.data.user.CreateUserRepoImplJava;
import com.brandon.appjava.data.user.CreateUserRepoJava;
import com.brandon.appjava.domain.login.LoginUseCaseJava;
import com.brandon.appjava.domain.user.CreateUserUseCaseImplJava;
import com.brandon.appjava.domain.user.CreateUserUseCaseJava;
import com.brandon.appjava.network.ForzenApiServiceJava;
import com.brandon.appjava.viewmodel.MyViewModelFactory;
import com.brandon.appjava.viewmodel.createaccount.LegacyCreateAccountViewModel;
import com.brandon.appjava.viewmodel.login.LegacyLoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;

@InstallIn({ViewModelComponent.class, ActivityComponent.class})
@Module
public abstract class CreateUserModuleJava {

    @Provides
    public CreateUserRepoJava providesCreateUserRepo(ForzenApiServiceJava forzenApiServiceJava) {
        return new CreateUserRepoImplJava(forzenApiServiceJava);
    }

    @Provides
    public CreateUserUseCaseJava providesCreateUserUseCase(CreateUserRepoJava createUserRepoJava) {
        return new CreateUserUseCaseImplJava(createUserRepoJava);
    }

    @Binds
    public abstract ViewModelProvider.Factory bindMyViewModelFactory(MyViewModelFactory factory);

    @Provides
    public static LegacyCreateAccountViewModel provideLoginViewModel(CreateUserUseCaseJava createUserUseCaseJava , ConnectivityManager connectivityManager) {
        return new LegacyCreateAccountViewModel(createUserUseCaseJava, connectivityManager);
    }

}
