package com.example.journal.db

object ATTENDANCE {
    const val attendance = "CREATE TABLE ATTENDANCE (" +
            "ID_student INTEGER NOT NULL," +
            "ID_pair INTEGER NOT NULL," +
            "CONSTRAINT PK_STUDENT_SCHEDULE PRIMARY KEY (ID_student, ID_pair), " +
            "YN BLOB" +
            "CONSTRAINT STUDENT_FK FOREIGN KEY (ID_student) REFERENCES STUDENTS(ID_student), " +
            "CONSTRAINT SCHEDULE_FK FOREIGN KEY (ID_pair) REFERENCES SCHEDULE(ID_pair))"
}