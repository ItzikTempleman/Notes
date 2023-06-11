package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.room_database.NoteDao
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun getNote(): Note =noteDao.getNote()

    override suspend fun saveNote(note: Note) =noteDao.saveNote(note)
}