package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.Note

interface NoteRepository {
    suspend fun emptyTrashBin()
    suspend fun getAllNotes(): MutableList<Note>
    suspend fun saveNote(note: Note)
    suspend fun saveDeletedNotesToTrashBin(notes:MutableList<Note>)
    suspend fun getAllDeletedNotes(): MutableList<Note>

    suspend fun archiveANote(note:Note)
}