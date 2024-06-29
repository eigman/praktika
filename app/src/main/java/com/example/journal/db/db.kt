package com.example.journal.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class db (val context: Context): SQLiteOpenHelper (context, DB_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(GROUP.group)
        db?.execSQL(TEACHER.teacher)
        db?.execSQL(DISCIPLINE.discipline)
        db?.execSQL(TEACHER_DISCIPLINE.teacher_discipline)
        db?.execSQL(STUDENTS.students)
        db?.execSQL(SCHEDULE.schedule)
        db?.execSQL(ATTENDANCE.attendance)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun executeQuery (sql: String) : Boolean {
        try {
            val database = this.writableDatabase
            database.execSQL(sql)
        }catch (e:Exception){
            e.printStackTrace()
            return false
        }
        return false
    }

    fun getData(){
        var cursor: Cursor?=null
        try {
            val database = this.readableDatabase
            cursor = database.rawQuery("select * from GROUP", null)
            cursor?.moveToFirst()
            do {
                var name = cursor.getColumnIndex("ID_GROUP")
                Log.d()
            }while (cursor.moveToNext())
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    companion object{
        private const val DB_VERSION = 1
        private const val DB_NAME = "PIDORI.DB"
    }
}