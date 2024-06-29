package com.example.journal.db

object DISCIPLINE {
    const val discipline = "CREATE TABLE DISCIPLINE (" +
            "ID_discipline INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NAME TEXT NOT NULL, " +
            "ID_group TEXT NOT NULL, " +
            "Type TEXT NOT NULL)"
}