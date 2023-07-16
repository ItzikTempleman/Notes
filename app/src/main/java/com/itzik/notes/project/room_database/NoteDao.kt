package com.itzik.notes.project.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrashBin=0" )
    suspend fun getAllNotes(): MutableList<Note>

    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrashBin=1" )
    suspend fun getAllDeletedNotes(): MutableList<Note>

    @Insert
    suspend fun saveNote(note: Note)

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAllNotes()

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNotesToTrashBin(notes: MutableList<Note>)
}