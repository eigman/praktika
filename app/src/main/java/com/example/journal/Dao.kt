package com.example.journal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface DeviceDao {
    @Insert
    fun insertGroup(item: Group)

    @Insert
    fun insertAttendance(item: Attendance)

    @Query("SELECT GROUP_NUMBER FROM GROUPS LIMIT 1")
    fun selectGroupNumber(): Int

    @Query("SELECT * FROM GROUPS")
    fun selectAllGroup(): Flow<List<Group>>

    @Query("DELETE FROM GROUPS")
    fun deleteAllGroup()

    @Insert
    fun insertStudent(item: Student)

    @Query("SELECT * FROM STUDENTS")
    fun selectStudents(): Flow<List<Student>>

    @Delete
    fun deleteStudent(student: Student)

    @Query("SELECT * FROM DISCIPLINES")
    fun selectDisciplines(): Flow<List<Discipline>>

    @Delete
    fun deleteDiscipline(discipline: Discipline)

    @Insert
    fun insertDiscipline(item: Discipline)

    @Query("SELECT ID_DISCIPLINE FROM DISCIPLINES WHERE NAME = :name")
    suspend fun getDisciplineIdByName(name: String): Int

    @Query("SELECT NAME FROM DISCIPLINES")
    fun selectAllDisciplineNames(): LiveData<List<String>>

    @Insert
    fun insertSchedule(item: Schedule)

    @Query("SELECT * FROM SCHEDULE")
    suspend fun selectAllSchedule(): List<Schedule>

    @Query("SELECT * FROM SCHEDULE WHERE ID_DISCIPLINE = :disciplineId")
    suspend fun selectScheduleByDiscipline(disciplineId: Int): List<Schedule>

    @Query("SELECT * FROM ATTENDANCE")
    suspend fun selectAllAttendance(): List<Attendance>

    @Query("SELECT * FROM ATTENDANCE WHERE ID_PAIR IN (SELECT ID_PAIR FROM SCHEDULE WHERE ID_DISCIPLINE = :disciplineId)")
    suspend fun selectAttendanceByDiscipline(disciplineId: Int): List<Attendance>
}
