package com.jgeron.quicknotes.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeron.quicknotes.data.Note
import com.jgeron.quicknotes.data.INotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        runBlocking {
            notesList = repository.getAllNotes()
        }
    }

    fun addNote(title: String){
        val note = Note(System.currentTimeMillis().toString(), title, "-")
        viewModelScope.launch(Dispatchers.IO){
            notesRepository.saveNote(note)
        }
        notesList.add(note)
        _noteLiveData.postValue(note)
        //_notesLiveData.postValue(notesList)
    }

    fun removeNote(id: String){
        //Log.d(TAG, "list size is : ${notesList.size}")
        val note = notesList.first {
            it.id == id
        }
        viewModelScope.launch(Dispatchers.IO){
            notesRepository.removeNote(id)
        }
        notesList.remove(note)
        _noteLiveData.postValue(note)
        //_notesLiveData.postValue(notesList)
    }

    fun updateNotesListFromStore(){
        viewModelScope.launch(Dispatchers.IO){
            notesList = notesRepository.getAllNotes()
            _notesLiveData.postValue(notesList)
        }
    }

}