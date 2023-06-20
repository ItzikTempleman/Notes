package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.Note
import com.itzik.notes.project.room_database.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

class NoteRepositoryImp @Inject constructor(

    @Singleton
    private val noteDao: NoteDao
    ) : NoteRepository {
    override suspend fun deleteAllNotes() = noteDao.deleteAllNotes()
    override suspend fun getAllNotes(): MutableList<Note> =noteDao.getAllNotes()
    override suspend fun saveNote(note: Note) =noteDao.saveNote(note)
}