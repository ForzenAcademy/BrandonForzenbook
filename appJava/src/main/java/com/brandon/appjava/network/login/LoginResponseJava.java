package com.brandon.appjava.network.login;

import java.util.Objects;

public class LoginResponseJava {
    private final String token;

    public LoginResponseJava(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponseJava that = (LoginResponseJava) o;
        return Objects.equals(token, that.token);
    }
}
