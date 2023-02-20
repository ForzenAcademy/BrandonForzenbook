package com.brandon.appjava.data;

import static com.brandon.appjava.data.ForzenEntityJava.LOGIN_TABLE_NAME;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = LOGIN_TABLE_NAME)
public class ForzenEntityJava {
    public static final String LOGIN_TABLE_NAME = "login_table";
    private static final String LOGIN_DATABASE_ID = "database_id";
    private static final String LOGIN_TIMESTAMP = "timestamp";
    private static final String LOGIN_TOKEN = "token";

    @ColumnInfo(name = LOGIN_TIMESTAMP) private final Long timestamp;
    @ColumnInfo(name = LOGIN_TOKEN) private final String token;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LOGIN_DATABASE_ID)
    private int id;

    public ForzenEntityJava(String token, Long timestamp) {
        this.token = token;
        this.timestamp = timestamp;
    }

    public String getToken() {
        return this.token;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForzenEntityJava that = (ForzenEntityJava) o;
        return Objects.equals(token, that.token)
                && Objects.equals(timestamp, that.timestamp)
                && Objects.equals(id, that.id);
    }

}
