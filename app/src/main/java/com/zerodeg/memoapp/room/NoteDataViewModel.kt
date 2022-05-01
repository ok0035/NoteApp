package com.zerodeg.memoapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteDataViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    //Livedata와 Flow의 차이는 Lifecycle을 인식할 수 있는지 없는지에 대한 차이
//    val notes:LiveData<List<Note>> = noteRepository.allNote.asLiveData()

    fun insert(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    fun update(id:Int, title:String, content: String, password: String) = viewModelScope.launch {
        noteRepository.update(id, title, content, password)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        noteRepository.deleteById(id)
    }

    fun deleteAll() = viewModelScope.launch {
        noteRepository.deleteAll()
    }

}
