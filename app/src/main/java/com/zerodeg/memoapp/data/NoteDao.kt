package com.zerodeg.memoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAll(): Flow<List<Note>>

    @Query("DELETE FROM Note")
    fun deleteAll()

    @Insert
    fun insert(note:Note)

    @Update
    fun update(note:Note)

    @Query("DELETE FROM Note WHERE id = :id")
    fun delete(id: Int)
}