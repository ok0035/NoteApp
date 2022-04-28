package com.zerodeg.memoapp.data

import kotlinx.coroutines.flow.Flow


class NoteRepository(private val noteDao: NoteDao) {
    val allNote: Flow<List<Note>> = noteDao.getAll()
}