package com.jgeron.quicknotes.data

import android.content.Context
import com.jgeron.quicknotes.data.Note

interface INotesRepository {

//    fun saveNote(note: Note, context: Context) : String
//    fun getAllNotes(context: Context) : MutableList<Note>
//    fun removeNote(id: String, context: Context)

    suspend fun saveNote(note: Note) : String
    suspend fun getAllNotes() : MutableList<Note>
    suspend fun removeNote(id: String)

}