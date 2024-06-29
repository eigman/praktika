package com.example.journal.db

object STUDENTS {
    const val students = "CREATE TABLE STUDENTS (" +
            "ID_student INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NAME TEXT NOT NULL, " +
            "SURNAME TEXT NOT NULL, " +
            "PATRONYMIC TEXT NOT NULL, " +
            "ID_group INTEGER, " +
            "CONSTRAINT GROUP_STUDENT_FK " +
            "FOREIGN KEY (ID_group) REFERENCES GROUP(ID_group))"
}