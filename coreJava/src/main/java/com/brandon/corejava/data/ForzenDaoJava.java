package com.brandon.corejava.data;

import static com.brandon.corejava.data.ForzenEntityJava.LOGIN_TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ForzenDaoJava {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ForzenEntityJava forzenEntityJava);

    @Query("SELECT * FROM " + LOGIN_TABLE_NAME)
    List<LoginTokenJava> getToken();

}
