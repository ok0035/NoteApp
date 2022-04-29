package com.zerodeg.memoapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAll(): Flow<List<Note>>

    @Insert
    fun insert(note:Note)

    @Update
    fun update(note:Note)

    @Delete
    fun delete(note:Note)

    @Query("DELETE FROM Note")
    fun deleteAll()

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteById(id: Int)
}