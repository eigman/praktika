package com.example.journal.db

object SCHEDULE {
    const val schedule = "CREATE TABLE SCHEDULE (" +
            "ID_pair INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Date_pair TEXT NOT NULL, " +
            "ID_discipline INTEGER NOT NULL, " +
            "Number_pair INTEGER NOT NULL," +
            "CONSTRAINT DISCIPLINE_SCHEDULE_FK" +
            "FOREIGN KEY (ID_discipline)" +
            "REFERENCES DISCIPLINE(ID_discipline))"
}