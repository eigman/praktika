package com.example.journal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "GROUPS")

data class group(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "AMOUNT_STUDENTS")
    var AMOUNT_STUDENTS: String,
    @ColumnInfo(name = "GROUP_NUMBER")
    var GROUP_NUMBER: String,

    )