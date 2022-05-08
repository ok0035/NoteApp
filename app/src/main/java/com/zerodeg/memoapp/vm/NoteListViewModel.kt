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

    val noteListLiveData: MutableLiveData<List<Note>> by lazy { MutableLiveData(mutableListOf()) }
    val noteLiveData: MutableLiveData<Note> = MutableLiveData()
    var currentNote :Note? = null

    fun loadNote(note: Note) {
        App.log("loadNote", "load")
        noteLiveData.postValue(note)
        currentNote = note
    }

    fun searchNote(searchingText: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            val currentNote = noteListLiveData.value as MutableList
            currentNote.clear()
            db.noteDao().search(searchingText).forEach { note ->
                currentNote.add(0, note)
                App.log("note", "search note -> ${note.title}")
            }
            App.log("noteList", "${currentNote.toList()}")
            noteListLiveData.postValue(currentNote)
        }
    }

    fun newNote() {
        App.log("newNote", "new")
        val note = Note("", "", null, false)
        noteLiveData.postValue(note)
        currentNote = note
    }

    fun updateNote(note: Note) {

    }

    fun insertNote(title: String, content: String, password: String?, isLock:Boolean) {
        /*TODO
        * ROOM을 통해 메모 저장
        * 리스트에 추가
        * 메인프래그먼트로 이동
        * 메인프래그먼트 recyclder view에 추가된 note item 추가
        * */
        val note = Note(title, content, password, isLock)
        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            db.noteDao().insert(note)
            addNote(note)
        }
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
            val currentList = noteListLiveData.value as MutableList<Note>
            currentList.clear()
            db.noteDao().getAll().forEach { note ->
                currentList.add(0, note)
                App.log("note", "update note -> ${note.title}")
            }
            CoroutineScope(Dispatchers.Main).launch {
                noteListLiveData.value = currentList
            }

        }
    }

//    fun loadSavedNote() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val db = NoteDatabase.getInstance(App.applicationContext())!!
//            db.noteDao().getAll().forEach { note ->
//                noteList.add(0, note)
//                App.log("note", "add note -> ${note.title}")
//            }
//            App.log("noteList", "${noteList.toList()}")
//            noteListLiveData.postValue(noteList)
//            db.close()
//
//        }
//    }

    fun deleteAllNote() {

        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            db.noteDao().deleteAll()
            val currentNoteList = noteListLiveData.value as MutableList<Note>
            currentNoteList.clear()
            noteListLiveData.postValue(currentNoteList)
        }
    }

    fun deleteByID(id:Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val db = NoteDatabase.getInstance(App.applicationContext())!!
            db.noteDao().deleteById(id)
            update()
        }
    }

}