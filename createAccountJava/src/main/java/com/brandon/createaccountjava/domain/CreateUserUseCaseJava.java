package com.brandon.createaccountjava.domain;

public interface CreateUserUseCaseJava {

    CreateAccountErrorJava invoke(
            String firstName,
            String lastName,
            String email,
            String dateOfBirth,
            String location
    );

}
