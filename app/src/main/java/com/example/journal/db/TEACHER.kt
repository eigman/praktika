package com.example.journal.db

object TEACHER {
    const val teacher = "CREATE TABLE TEACHER (" +
            "ID_teacher INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NAME TEXT NOT NULL, " +
            "SURNAME TEXT NOT NULL, " +
            "PATRONYMIC TEXT NOT NULL)"
}