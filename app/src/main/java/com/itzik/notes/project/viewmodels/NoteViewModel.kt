package com.itzik.notes.project.viewmodels

import androidx.lifecycle.ViewModel
import com.itzik.notes.project.models.note.Note
import com.itzik.notes.project.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    suspend fun getNote()=repository.getNote()

    suspend fun saveNote(note:Note)=repository.saveNote(note)
}
