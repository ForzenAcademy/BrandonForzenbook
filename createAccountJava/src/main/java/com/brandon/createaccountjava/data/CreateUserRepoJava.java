package com.brandon.createaccountjava.data;

import java.sql.Date;

public interface CreateUserRepoJava {

    void createUser(
            String firstName,
            String lastName,
            String email,
            Date dateOfBirth,
            String location
    ) throws Exception;

}
