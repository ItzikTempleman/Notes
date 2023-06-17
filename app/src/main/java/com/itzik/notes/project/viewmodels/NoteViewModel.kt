package com.itzik.notes.project.viewmodels

import androidx.lifecycle.ViewModel
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.repositories.NoteRepository
import com.itzik.notes.project.screens.note_screens.noteList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val repository: NoteRepository
    ) : ViewModel() {

    suspend fun getNote() = repository.getNote()

    suspend fun getAllNotes() = repository.getAllNotes()

    suspend fun saveNote(note: Note) = repository.saveNote(note)

    fun updateNote(note: Note) {
        noteList.add(note)
    }
}
