package com.example.forzenbook.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ForzenDao {

    @Insert
    suspend fun insert(forzenEntity: ForzenEntity)

    @Query(
        """
            SELECT * FROM ${ForzenEntity.LOGIN_TABLE_NAME}
        """
    )
    suspend fun getLoginToken(): ForzenEntity?

    @Query(
        """
            DELETE FROM ${ForzenEntity.LOGIN_TABLE_NAME}
        """
    )
    suspend fun deleteLoginToken()
}