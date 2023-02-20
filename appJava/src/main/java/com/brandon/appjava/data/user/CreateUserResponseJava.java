package com.brandon.appjava.data.user;

import java.util.Objects;

public class CreateUserResponseJava {
    private final String reason;


    public CreateUserResponseJava(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateUserResponseJava)) return false;
        CreateUserResponseJava that = (CreateUserResponseJava) o;
        return Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason);
    }
}
