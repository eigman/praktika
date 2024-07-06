package com.example.journal

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database
    (entities =
    [group::class,
    students::class,
    disciplines::class,
    attendance::class,
    schedule::class],
    version = 1)


abstract class mainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    companion object{
        fun getDb(context: Context): mainDb{
            return Room.databaseBuilder(
                context.applicationContext,
                mainDb::class.java,
                "journal.db"
            ).build()
        }
    }
}

