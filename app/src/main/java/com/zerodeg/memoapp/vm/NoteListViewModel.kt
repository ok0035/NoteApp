package com.zerodeg.memoapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.room.Note
import com.zerodeg.memoapp.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteListViewModel : ViewModel() {

    private val noteList = mutableListOf<Note>()
    val noteListLiveData: MutableLiveData<List<Note>> by lazy { MutableLiveData(noteList) }
    val noteLiveData: MutableLiveData<Note> = MutableLiveData()

    fun loadNote(note:Note) {
        App.log("loadNote", "load")
        noteLiveData.postValue(note)
    }

    fun updateNote(note:Note) {

    }

    fun insertNote(note:Note) {
        /*TODO
        * ROOM을 통해 메모 저장
        * 리스트에 추가
        * 메인프래그먼트로 이동
        * 메인프래그먼트 recyclder view에 추가된 note item 추가
        * */

        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            db.noteDao().insert(note)
            addNote(note)
            db.close()
        }
    }

    fun addNote(note: Note) {

        val currentList = noteListLiveData.value
        if (currentList == null) {
            App.log("addNote", "if null")
            noteListLiveData.postValue(listOf(note))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, note)
            App.log("addNote", "if not null -> ${updatedList.toList()}")
            noteListLiveData.postValue(updatedList)
        }
    }

    fun update() { // update from note database
        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            noteList.clear()
            db.noteDao().getAll().collect {
                it.forEach { note ->
                    noteList.add(0, note)
                    App.log("note", "update note -> ${note.title}")
                }
                App.log("noteList", "${noteList.toList()}")
                noteListLiveData.postValue(noteList)
            }

            db.close()

        }
    }

    fun loadSavedNote() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            db.noteDao().getAll().collect {
                it.forEach { note ->
                    noteList.add(0, note)
                    App.log("note", "add note -> ${note.title}")
                }
                App.log("noteList", "${noteList.toList()}")
                noteListLiveData.postValue(noteList)
            }

            db.close()

        }
    }

    fun deleteAllNote() {

        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            db.noteDao().deleteAll()
            db.close()

            noteList.clear()
            noteListLiveData.postValue(noteList)
        }
    }

}