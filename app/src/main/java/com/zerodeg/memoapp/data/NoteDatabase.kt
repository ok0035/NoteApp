package com.zerodeg.memoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zerodeg.memoapp.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "note-database"
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase? {
            if(instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return instance

        }

    }
}