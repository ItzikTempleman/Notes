package com.itzik.notes.project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.itzik.notes.project.model.Note
import com.itzik.notes.project.utils.Constants.NOTE_TABLE

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNote(note: Note)

    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrash = 0 AND userId = :userId")
    suspend fun fetchNotes(userId: String): MutableList<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleNoteIntoRecycleBin(note: Note)

    @Update
    suspend fun setTrash(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteListIntoRecycleBin(notes: MutableList<Note>)

    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrash = 1 AND userId = :userId")
    suspend fun fetchTrashedNotes(userId: String): MutableList<Note>

    @Query("DELETE FROM $NOTE_TABLE WHERE isInTrash=1")
    suspend fun emptyTrashBin()

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM $NOTE_TABLE WHERE isStarred = 1 AND userId = :userId")
    suspend fun fetchStarredNotes(userId: String): MutableList<Note>

    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrash = 0 AND userId = :userId ORDER BY isPinned DESC, content ASC")
    suspend fun fetchNotesSortedAlphabetically(userId: String): MutableList<Note>

    @Query("SELECT * FROM $NOTE_TABLE WHERE isInTrash = 0 AND userId = :userId ORDER BY isPinned DESC, time ASC")
    suspend fun fetchNotesSortedByDateModified(userId: String): MutableList<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("UPDATE $NOTE_TABLE SET fontWeight = :fontWeight WHERE noteId = :noteId")
    suspend fun updateFontWeight(noteId: Int, fontWeight: Int)
}

