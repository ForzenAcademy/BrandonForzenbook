package com.brandon.loginmodulejava.domain;

import javax.annotation.Nullable;

public interface LoginUseCaseJava {

    LoginValidationStateJava invoke(String email, @Nullable String code);

}
