package com.jgeron.quicknotes.data

import android.content.Context
import com.jgeron.quicknotes.data.Note

interface INotesRepository {

//    fun saveNote(note: Note, context: Context) : String
//    fun getAllNotes(context: Context) : MutableList<Note>
//    fun removeNote(id: String, context: Context)

    fun saveNote(note: Note) : String
    fun getAllNotes() : MutableList<Note>
    fun removeNote(id: String)

}