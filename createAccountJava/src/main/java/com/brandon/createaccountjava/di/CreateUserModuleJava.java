package com.brandon.createaccountjava.di;

import android.net.ConnectivityManager;

import com.brandon.corejava.network.ForzenApiServiceJava;
import com.brandon.createaccountjava.data.CreateUserRepoImplJava;
import com.brandon.createaccountjava.data.CreateUserRepoJava;
import com.brandon.createaccountjava.domain.CreateUserUseCaseImplJava;
import com.brandon.createaccountjava.domain.CreateUserUseCaseJava;
import com.brandon.createaccountjava.viewmodel.LegacyCreateAccountViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;

@InstallIn({ViewModelComponent.class, ActivityComponent.class})
@Module
public class CreateUserModuleJava {

    @Provides
    public CreateUserRepoJava providesCreateUserRepo(ForzenApiServiceJava forzenApiServiceJava) {
        return new CreateUserRepoImplJava(forzenApiServiceJava);
    }

    @Provides
    public CreateUserUseCaseJava providesCreateUserUseCase(CreateUserRepoJava createUserRepoJava) {
        return new CreateUserUseCaseImplJava(createUserRepoJava);
    }

    @Provides
    public static LegacyCreateAccountViewModel provideLoginViewModel(
            CreateUserUseCaseJava createUserUseCaseJava,
            ConnectivityManager connectivityManager
    ) {
        return new LegacyCreateAccountViewModel(createUserUseCaseJava, connectivityManager);
    }

}
