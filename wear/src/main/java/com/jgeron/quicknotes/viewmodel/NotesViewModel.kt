package com.jgeron.quicknotes.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgeron.quicknotes.data.Note
import com.jgeron.quicknotes.data.INotesRepository

class NotesViewModel constructor(
    repository: INotesRepository
): ViewModel() {

    private val TAG = "NotesViewModel"

    private var notesRepository: INotesRepository = repository
    private var _noteLiveData = MutableLiveData<Note>()
    var noteLiveData: LiveData<Note> = _noteLiveData

    private var _notesLiveData = MutableLiveData<List<Note>>()
    var notesLiveData: LiveData<List<Note>> = _notesLiveData

    var notesList: MutableList<Note>
        private set

    init {
        //Log.d(TAG, "VIEW MODEL CREATED")
        notesList = repository.getAllNotes()
    }

    fun addNote(title: String){
        val note = Note(System.currentTimeMillis().toString(), title, "-")
        notesRepository.saveNote(note)
        notesList.add(note)
        _noteLiveData.postValue(note)
        //_notesLiveData.postValue(notesList)
    }

    fun removeNote(id: String){
        //Log.d(TAG, "list size is : ${notesList.size}")
        val note = notesList.first {
            it.id == id
        }
        notesRepository.removeNote(id)
        notesList.remove(note)
        _noteLiveData.postValue(note)
        //_notesLiveData.postValue(notesList)
    }

    fun updateNotesListFromStore(){
        notesList = notesRepository.getAllNotes()
        _notesLiveData.postValue(notesList)
    }

}