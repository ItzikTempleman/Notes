package com.itzik.notes.project.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE")
    suspend fun getAllNotes(): MutableList<Note>

    @Insert
    suspend fun saveNote(note: Note)

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAllNotes()
}