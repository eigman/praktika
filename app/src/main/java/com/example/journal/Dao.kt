package com.example.journal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao{
    @Insert
    fun insertGroup(item: Group)

    @Query ("SELECT GROUP_NUMBER FROM GROUPS LIMIT 1")
    fun selectGroupNumber(): Int?


    @Insert
    fun insertStudent(item: Student)
}