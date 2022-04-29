package com.zerodeg.memoapp.ui.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.room.Note
import com.zerodeg.memoapp.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel : ViewModel() {

    private val noteList = mutableListOf<Note>()
    val noteLiveData: LiveData<MutableList<Note>> = MutableLiveData(noteList)

    fun insertNote(title: String, content: String) {
        /*TODO
        * ROOM을 통해 메모 저장
        * 리스트에 추가
        * 메인프래그먼트로 이동
        * 메인프래그먼트 recyclder view에 추가된 note item 추가
        * */

        val note = Note(title, content)
        val db = NoteDatabase.getInstance(App.applicationContext())
        CoroutineScope(Dispatchers.IO).launch {
            db!!.noteDao().insert(note)
            noteList.add(note)
        }
    }
}