package com.itzik.notes.project.room_database

import androidx.compose.runtime.State
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {
    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrashBin=:deletedOnly" )
    suspend fun getNotes(deletedOnly: Boolean = false): MutableList<Note>

    @Insert
    suspend fun saveNote(note: Note)

    @Query("DELETE FROM $NOTE_TABLE WHERE isInTrashBin=1")
    suspend fun emptyTrashBin()

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotesToTrashBin(notes: MutableList<Note>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun archiveSingleNote(note: Note)

}