package com.example.journal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao{
    @Insert
    fun insertGroup(item: Group)
    @Query ("SELECT GROUP_NUMBER FROM GROUPS LIMIT 1")
    fun selectGroupNumber(): Int
    @Query ("SELECT * FROM GROUPS")
    fun selectAllGroup(): Flow<List<Group>>
    @Query ("DELETE FROM GROUPS")
    fun deleteAllGroup()

    @Insert
    fun insertStudent(item: Student)
    @Query ("SELECT * FROM STUDENTS")
    fun selectStudents(): Flow<List<Student>>
    @Delete
    fun deleteStudent(student: Student)

    @Query ("SELECT * FROM DISCIPLINES")
    fun selectDisciplines(): Flow<List<Discipline>>
    @Delete
    fun deleteDiscipline(discipline: Discipline)
    @Query("SELECT NAME FROM DISCIPLINES")
    fun getAllDisciplineNames(): List<String>
    @Insert
    fun insertDiscipline(item: Discipline)

    @Query("""
        SELECT s.*, COUNT(a.ID_PAIR) AS attendanceCount 
        FROM STUDENTS s 
        LEFT JOIN ATTENDANCE a ON s.ID_STUDENT = a.ID_STUDENT 
        LEFT JOIN SCHEDULE sc ON a.ID_PAIR = sc.ID_PAIR 
        LEFT JOIN DISCIPLINES d ON sc.ID_DISCIPLINE = d.ID_DISCIPLINE 
        WHERE d.NAME = :disciplineName 
        GROUP BY s.ID_STUDENT
    """)
    fun getAttendanceForDiscipline(disciplineName: String): Flow<List<StudentWithAttendance>>

    @Insert
    fun insertSchedule(item: Schedule)
}