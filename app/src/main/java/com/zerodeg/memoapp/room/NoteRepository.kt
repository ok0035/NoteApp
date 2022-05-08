package com.zerodeg.memoapp.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class NoteRepository(private val noteDao: NoteDao) {
    val allNote: List<Note> = noteDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(id:Int, title:String, content: String, password:String, isLock:Boolean) {
        noteDao.update(id, title, content, password, isLock)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteById(id:Int) {
        noteDao.deleteById(id)
    }
}