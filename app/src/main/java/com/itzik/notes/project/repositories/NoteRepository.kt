package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.note.Note

interface NoteRepository {
    suspend fun getAllNotes(): MutableList<Note>
    suspend fun saveNote(note: Note)

}