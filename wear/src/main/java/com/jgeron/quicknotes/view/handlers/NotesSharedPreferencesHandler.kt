package com.jgeron.quicknotes.view.handlers

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.jgeron.quicknotes.data.Note
import com.jgeron.quicknotes.data.INotesRepository
import java.lang.ref.WeakReference

/**
 * A  NotesSharedPreferencesHandler class used to Store data.
 * Use the bind method in onCreate to bind this method to activity
 * and be sure object of this class will be removed in onDestroy to prevent memory leaks.
 * I am implement it in this way because I want to have clean INotesRepository storage
 * but I don't want to use any DI tools like Dagger or Hilt to create VM
 */
class NotesSharedPreferencesHandler private constructor(val context: WeakReference<Context>  ) : INotesRepository{

    private val TAG = "NotesSharedPreferencesHandler"

    companion object{
        fun bind(context: Context): NotesSharedPreferencesHandler{
            return NotesSharedPreferencesHandler(WeakReference(context))
        }
    }

    override suspend fun saveNote(note: Note) : String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.get())
        val editor = sharedPreferences.edit()

        editor.putStringSet(note.id, mutableSetOf(note.title, note.description))
        editor.apply()

        return note.id
    }

    override suspend fun getAllNotes() : MutableList<Note>{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.get())
        val notesList: MutableList<Note> = ArrayList()
        val dataSet = sharedPreferences.all
        dataSet.forEach{
            val savedData = it.value as MutableSet<String>?
            if (savedData != null){
                val note = Note(it.key, savedData.first(), savedData.last())
                Log.d(TAG, "get node: ${note.id}")
                notesList.add(note)
            }
        }
        return notesList
    }

    override suspend fun removeNote(id: String){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.get())
        val editor = sharedPreferences.edit()
        editor.remove(id);
        editor.apply()
    }


}