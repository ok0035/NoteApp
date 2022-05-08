package com.zerodeg.memoapp.room

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM Note WHERE title LIKE '%' || :searching || '%'")
    fun search(searching: String?): List<Note>

    @Insert
    fun insert(note:Note)

    @Query("UPDATE Note SET title=:title, content=:content, password=:password, isLock=:isLock WHERE id = :id")
    fun update(id:Int, title:String, content: String, password: String, isLock:Boolean)

    @Delete
    fun delete(note:Note)

    @Query("DELETE FROM Note")
    fun deleteAll()

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT count(*)!=0 FROM Note WHERE id = :id ")
    fun containsPrimaryKey(id: Int): Boolean
}