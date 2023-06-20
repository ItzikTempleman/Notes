package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.Note

interface NoteRepository {
    suspend fun deleteAllNotes()
    suspend fun getAllNotes(): MutableList<Note>
    suspend fun saveNote(note: Note)

}