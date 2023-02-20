package com.brandon.appjava.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ForzenDaoJava {

    @Insert
    void insert(ForzenEntityJava forzenEntityJava);

    @Query("SELECT * FROM login_table")
    List<LoginTokenJava> getToken();

    @Query("DELETE FROM login_table")
    void deleteToken();
}
