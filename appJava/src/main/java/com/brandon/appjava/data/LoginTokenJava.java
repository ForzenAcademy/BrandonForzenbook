package com.brandon.appjava.data;

import com.brandon.appjava.network.login.LoginResponseJava;

import java.util.Objects;

public class LoginTokenJava {
    private final String token;

    public LoginTokenJava(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginTokenJava that = (LoginTokenJava) o;
        return Objects.equals(token, that.token);
    }
}
