package com.example.journal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeviceDao {
    @Insert
    fun insertGroup(item: Group)

    @Query("SELECT GROUP_NUMBER FROM GROUPS LIMIT 1")
    fun selectGroupNumber(): Int?

    @Query("SELECT * FROM STUDENTS")
    fun getStudents(): LiveData<List<Student>>

    @Query("""
    SELECT s.ID_PAIR, d.NAME, s.TYPE 
    FROM SCHEDULE s
    JOIN DISCIPLINES d ON s.ID_DISCIPLINE = d.ID_DISCIPLINE
    WHERE s.DATE_PAIR = :date
    """)
    fun getPair(date: String): List<PairWithDiscipline>

    @Query("SELECT * FROM ATTENDANCE")
    fun getAtt(): LiveData<List<Attendance>>

    @Query("SELECT * FROM ATTENDANCE")
    fun getAttDirect(): List<Attendance>

    @Query("UPDATE ATTENDANCE SET `Y/N` =:count WHERE ID_PAIR =:idOfPair AND ID_STUDENT =:idOfStudent")
    fun updateAttendance(idOfPair: Int, idOfStudent: Int, count: Int)

    @Insert
    fun insertStudent(item: Student)

    @Query("SELECT * FROM STUDENTS")
    fun getStudentsDirect(): List<Student>
}