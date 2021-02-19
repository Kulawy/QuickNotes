package com.jgeron.quicknotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgeron.quicknotes.data.INotesRepository
import java.lang.IllegalArgumentException

class NotesViewModelFactory(private val INotesRepository: INotesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(INotesRepository) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }

}