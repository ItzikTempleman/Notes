package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.note.Note

interface NoteRepository {
    suspend fun getNote():Note

    suspend fun saveNote(note: Note)
}