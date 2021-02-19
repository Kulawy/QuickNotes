package com.jgeron.quicknotes.data

import androidx.annotation.VisibleForTesting

class FakeStorage: INotesRepository {

    @VisibleForTesting
    private val storage = HashSet<Note>()

    override fun saveNote(note: Note): String {
        storage.add(note)
        return note.id
    }

    override fun getAllNotes(): MutableList<Note> {
        return storage.toMutableList()
    }

    override fun removeNote(id: String) {
        storage.removeIf { n -> n.id == id }
    }
}