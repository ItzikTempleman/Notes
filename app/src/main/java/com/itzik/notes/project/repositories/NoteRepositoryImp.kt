package com.itzik.notes.project.repositories

import com.itzik.notes.project.models.Note
import com.itzik.notes.project.room_database.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

class NoteRepositoryImp @Inject constructor(

    @Singleton
    private val noteDao: NoteDao,
) : NoteRepository {
    override suspend fun emptyTrashBin() = noteDao.emptyTrashBin()
    override suspend fun getAllNotes(): MutableList<Note> = noteDao.getNotes()
    override suspend fun saveNote(note: Note) = noteDao.saveNote(note)
    override suspend fun saveDeletedNotesToTrashBin(notes: MutableList<Note>) =noteDao.saveNotesToTrashBin(notes)
    override suspend fun getAllDeletedNotes(): MutableList<Note> = noteDao.getNotes(true)
    override suspend fun archiveANote(note: Note) =noteDao.archiveSingleNote(note)

}