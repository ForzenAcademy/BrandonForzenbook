package com.brandon.createaccountjava.di;

import com.brandon.corejava.network.ForzenApiServiceJava;
import com.brandon.createaccountjava.data.CreateUserRepoImplJava;
import com.brandon.createaccountjava.data.CreateUserRepoJava;
import com.brandon.createaccountjava.domain.CreateUserUseCaseImplJava;
import com.brandon.createaccountjava.domain.CreateUserUseCaseJava;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@InstallIn(ViewModelComponent.class)
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
}
