package com.example.journal.db

object TEACHER_DISCIPLINE {
    const val teacher_discipline = "CREATE TABLE TEACHER_DISCIPLINE (" +
            "ID_teacher INTEGER NOT NULL," +
            "ID_discipline INTEGER NOT NULL," +
            "CONSTRAINT PK_TEACHER_DISCIPLINE PRIMARY KEY (ID_teacher, ID_discipline), " +
            "CONSTRAINT TEACHER_FK FOREIGN KEY (ID_teacher) REFERENCES TEACHER(ID_teacher), " +
            "CONSTRAINT DISCIPLINE_FK FOREIGN KEY (ID_discipline) REFERENCES DISCIPLINE(ID_discipline))"
}