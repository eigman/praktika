package com.example.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao

interface Dao {
    @Insert
    fun insertGroup(item: group)
    @Query("SELECT * FROM GROUPS")
    fun getAllGroup(): Flow<List<group>>
}