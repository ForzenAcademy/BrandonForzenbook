package com.brandon.loginmodulejava.data;

public interface LoginRepoJava {

    void getCode(String email) throws Exception;

    void getToken(String email, String code) throws Exception;
}
