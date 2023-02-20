package com.brandon.appjava.data.user;

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
