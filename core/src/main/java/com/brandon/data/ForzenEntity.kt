package com.brandon.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brandon.data.ForzenEntity.Companion.LOGIN_TABLE_NAME
import com.brandon.network.login.LoginResponse

@Entity(tableName = LOGIN_TABLE_NAME)
data class ForzenEntity(
    @ColumnInfo(name = LOGIN_TIMESTAMP) val timestamp: Long,
    @ColumnInfo(name = LOGIN_TOKEN) val token: String?,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LOGIN_DATABASE_ID)
    var databaseId: Int = 0

    companion object {
        const val LOGIN_TABLE_NAME = "login_table"
        const val LOGIN_DATABASE_ID = "database_id"
        const val LOGIN_TIMESTAMP = "timestamp"
        const val LOGIN_TOKEN = "token"
    }
}

fun LoginResponse.toForzenEntity(): ForzenEntity {
    return ForzenEntity(
        timestamp = System.currentTimeMillis(),
        token = token
    )
}