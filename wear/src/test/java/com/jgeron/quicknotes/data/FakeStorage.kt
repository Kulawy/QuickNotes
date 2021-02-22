package com.jgeron.quicknotes.data

import androidx.annotation.VisibleForTesting

class FakeStorage: INotesRepository {

    @VisibleForTesting
    private val storage = HashSet<Note>()

    override suspend fun saveNote(note: Note): String {
        storage.add(note)
        return note.id
    }

    override suspend fun getAllNotes(): MutableList<Note> {
        return storage.toMutableList()
    }

    override suspend fun removeNote(id: String) {
        storage.removeIf { n -> n.id == id }
    }
}