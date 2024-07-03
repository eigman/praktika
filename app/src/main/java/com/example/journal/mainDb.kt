package com.example.journal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database
    (entities =
    [group::class,
    students::class,
    disciplines::class,
    teachers::class,
    attendance::class,
    schedule::class,
    teacher_discipline::class],
    version = 2)


abstract class mainDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        private var INSTANCE: mainDb? = null

        fun getDb(context: Context): mainDb {
            if (INSTANCE == null) {
                synchronized(mainDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        mainDb::class.java,
                        "journal.db"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE!!
        }


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Создание таблицы STUDENTS
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `STUDENTS` (
                        `ID_STUDENT` INTEGER PRIMARY KEY AUTOINCREMENT,
                        `GROUP_NUMBER` INTEGER NOT NULL,
                        `NAME` TEXT NOT NULL,
                        `SURNAME` TEXT NOT NULL,
                        `PATRONYMIC` TEXT,
                        CONSTRAINT GROUP_STUDENT
                        FOREIGN KEY(`GROUP_NUMBER`) REFERENCES `GROUPS`(`GROUP_NUMBER`)
                    )
                """.trimIndent()
                )

                // Создание таблицы DISCIPLINES
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `DISCIPLINES` (
                        `ID_DISCIPLINE` INTEGER PRIMARY KEY AUTOINCREMENT,
                        `NAME` TEXT NOT NULL,
                        `GROUP_NUMBER` INTEGER NOT NULL,
                        `TYPE` TEXT NOT NULL,
                        CONSTRAINT DISCIPLINE_GROUP
                        FOREIGN KEY(`GROUP_NUMBER`) REFERENCES `GROUPS`(`GROUP_NUMBER`)
                    )
                """.trimIndent()
                )

                // Создание таблицы SCHEDULE
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `SCHEDULE` (
                        `ID_PAIR` INTEGER PRIMARY KEY AUTOINCREMENT,
                        `ID_DISCIPLINE` INTEGER NOT NULL,
                        `DATE_PAIR` TEXT NOT NULL,
                        `TIME_PAIR` TEXT NOT NULL,
                        CONSTRAINT SCHEDULE_DISCIPLINE
                        FOREIGN KEY(`ID_DISCIPLINE`) REFERENCES `DISCIPLINES`(`ID_DISCIPLINE`)
                    )
                """.trimIndent()
                )

                // Создание таблицы TEACHERS
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `TEACHERS` (
                        `ID_TEACHER` INTEGER PRIMARY KEY AUTOINCREMENT,
                        `NAME` TEXT NOT NULL,
                        `SURNAME` TEXT NOT NULL,
                        `PATRONYMIC` TEXT NOT NULL
                    )
                """.trimIndent()
                )

                // Создание таблицы TEACHER_DISCIPLINE
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `TEACHER_DISCIPLINE` (
                        `ID_TEACHER` INTEGER NOT NULL,
                        `ID_DISCIPLINE` INTEGER NOT NULL,
                        PRIMARY KEY(`ID_TEACHER`, `ID_DISCIPLINE`),
                        CONSTRAINT TEACHER_DISCIPLINE
                        FOREIGN KEY(`ID_TEACHER`) REFERENCES `TEACHERS`(`ID_TEACHER`),
                        FOREIGN KEY(`ID_DISCIPLINE`) REFERENCES `DISCIPLINES`(`ID_DISCIPLINE`)
                    )
                """.trimIndent()
                )

                // Создание таблицы ATTENDANCE
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ATTENDANCE` (
                        `ID_STUDENT` INTEGER NOT NULL,
                        `ID_PAIR` INTEGER NOT NULL,
                        `Y/N` INTEGER NOT NULL,
                        PRIMARY KEY(`ID_STUDENT`, `ID_PAIR`),
                        CONSTRAINT STUDENT_ATTENDANCE
                        FOREIGN KEY(`ID_PAIR`) REFERENCES `SCHEDULE`(`ID_PAIR`),
                        FOREIGN KEY(`ID_STUDENT`) REFERENCES `STUDENTS`(`ID_STUDENT`)
                    )
                """.trimIndent()
                )
            }
        }
    }
}


