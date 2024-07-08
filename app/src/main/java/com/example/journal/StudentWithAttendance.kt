package com.example.journal

import androidx.room.Embedded
import androidx.room.Relation

data class StudentWithAttendance(
    @Embedded val student: Student,
    val attendanceCount: Int
)
