package com.brandon.appjava.domain.user;

public interface CreateUserUseCaseJava {

    CreateAccountErrorJava invoke(
            String firstName,
            String lastName,
            String email,
            String dateOfBirth,
            String location
    );

}
