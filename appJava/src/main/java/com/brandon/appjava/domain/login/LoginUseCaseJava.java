package com.brandon.appjava.domain.login;

import com.brandon.appjava.utilities.UserInputErrors;

import javax.annotation.Nullable;

public interface LoginUseCaseJava {

    LoginValidationStateJava invoke(String email, @Nullable String code);

}
