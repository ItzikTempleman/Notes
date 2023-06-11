package com.itzik.notes.project.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE")
    suspend fun getNote(): Note

    @Insert
    suspend fun saveNote(note: Note)


}