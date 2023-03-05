package com.brandon.loginmodulejava.di;

import com.brandon.corejava.data.ForzenDaoJava;
import com.brandon.corejava.network.ForzenApiServiceJava;
import com.brandon.loginmodulejava.data.LoginRepoImplJava;
import com.brandon.loginmodulejava.data.LoginRepoJava;
import com.brandon.loginmodulejava.domain.LoginUseCaseImplJava;
import com.brandon.loginmodulejava.domain.LoginUseCaseJava;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@InstallIn(ViewModelComponent.class)
@Module
public class LoginModuleJava {

    @Provides
    public static LoginRepoJava providesLoginRepo(ForzenApiServiceJava forzenApiServiceJava, ForzenDaoJava forzenDaoJava) {
        return new LoginRepoImplJava(forzenApiServiceJava, forzenDaoJava);
    }

    @Provides
    public static LoginUseCaseJava providesLoginUseCase(LoginRepoJava loginRepoJava) {
        return new LoginUseCaseImplJava(loginRepoJava);
    }
}
