package com.zerodeg.memoapp.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}