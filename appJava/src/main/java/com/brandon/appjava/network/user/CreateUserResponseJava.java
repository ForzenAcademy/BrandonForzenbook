package com.brandon.appjava.network.user;

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
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserResponseJava that = (CreateUserResponseJava) o;
        return Objects.equals(reason, that.reason);
    }
}
