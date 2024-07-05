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

    @Insert
    fun insertTeacher(item: teachers)
    @Query ("SELECT * FROM TEACHERS")
    fun getAllTeachers(): Flow<List<teachers>>

    @Insert
    fun insertStudent(item: students)
    @Query ("SELECT * FROM STUDENTS GROUP BY SURNAME")
    fun getStudents(): Flow<List<students>>

    @Query ("SELECT GROUP_NUMBER FROM GROUPS")
    fun getGroupNumber(): Int
}