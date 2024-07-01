package com.example.journal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity (tableName = "GROUPS")

data class group(
    @PrimaryKey
    var GROUP_NUMBER: Int,
    @ColumnInfo(name = "AMOUNT_STUDENTS")
    var AMOUNT_STUDENTS: String
)

@Entity(
    tableName = "STUDENTS",
    foreignKeys = [ForeignKey(
        entity = group::class,
        parentColumns = ["GROUP_NUMBER"],
        childColumns = ["GROUP_NUMBER"]
    )]
)

data class students(
    @PrimaryKey (autoGenerate = true)
    var ID_STUDENT: Int? = null,
    @ColumnInfo(name = "GROUP_NUMBER")
    var GROUP_NUMBER: Int,
    @ColumnInfo(name = "NAME")
    var NAME: String,
    @ColumnInfo(name = "SURNAME")
    var SURNAME: String,
    @ColumnInfo(name = "PATRONYMIC")
    var PATRONYMIC: String
)

@Entity(
    tableName = "DISCIPLINES",
    foreignKeys = [ForeignKey(
        entity = group::class,
        parentColumns = ["GROUP_NUMBER"],
        childColumns = ["GROUP_NUMBER"]
    )]
)

data class disciplines(
    @PrimaryKey (autoGenerate = true)
    var ID_DISCIPLINE: Int? = null,
    @ColumnInfo(name = "NAME")
    var NAME: String,
    @ColumnInfo(name = "GROUP_NUMBER")
    var GROUP_NUMBER: Int,
    @ColumnInfo(name = "TYPE")
    var TYPE: String
)

@Entity(
    tableName = "SCHEDULE",
    foreignKeys = [ForeignKey(
        entity = disciplines::class,
        parentColumns = ["ID_DISCIPLINE"],
        childColumns = ["ID_DISCIPLINE"]
    )]
)

data class schedule(
    @PrimaryKey (autoGenerate = true)
    var ID_PAIR: Int? = null,
    @ColumnInfo(name = "ID_DISCIPLINE")
    var ID_DISCIPLINE: Int,
    @ColumnInfo(name = "DATE_PAIR")
    var DATE_PAIR: String,
    @ColumnInfo(name = "TIME_PAIR")
    var TIME_PAIR: String
)

@Entity (tableName = "TEACHERS")

data class teachers(
    @PrimaryKey (autoGenerate = true)
    var ID_TEACHER: Int? = null,
    @ColumnInfo(name = "NAME")
    var NAME: String,
    @ColumnInfo(name = "SURNAME")
    var SURNAME: String,
    @ColumnInfo(name = "PATRONYMIC")
    var PATRONYMIC: String
)

@Entity (
    tableName = "TEACHER_DISCIPLINE",
    primaryKeys = ["ID_TEACHER", "ID_DISCIPLINE"],
    foreignKeys = [
        ForeignKey(
            entity = teachers::class,
            parentColumns = ["ID_TEACHER"],
            childColumns = ["ID_TEACHER"]
        ),
        ForeignKey(
            entity = disciplines::class,
            parentColumns = ["ID_DISCIPLINE"],
            childColumns = ["ID_DISCIPLINE"]
        )
    ]
)

data class teacher_discipline(
    @ColumnInfo(name = "ID_TEACHER")
    val ID_TEACHER: Int,
    @ColumnInfo(name = "ID_DISCIPLINE")
    val ID_DISCIPLINE: Int
)

@Entity (
    tableName = "ATTENDANCE",
    primaryKeys = ["ID_STUDENT", "ID_PAIR"],
    foreignKeys = [
        ForeignKey(
            entity = schedule::class,
            parentColumns = ["ID_PAIR"],
            childColumns = ["ID_PAIR"]
        ),
        ForeignKey(
            entity = students::class,
            parentColumns = ["ID_STUDENT"],
            childColumns = ["ID_STUDENT"]
        )
    ]
)

data class attendance(
    @ColumnInfo(name = "Y/N")
    val YESORNO: Int
)
