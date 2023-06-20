package com.itzik.notes.project.viewmodels

import androidx.lifecycle.ViewModel
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.repositories.NoteRepository
import com.itzik.notes.project.screens.note_screens.noteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val repository: NoteRepository
    ) : ViewModel() {


    suspend fun getAllNotes():Flow<MutableList<Note>>{
        val noteList = flow {
            val updateFlowList=repository.getAllNotes()
            if(updateFlowList.isNotEmpty()){
                emit(updateFlowList)
            }else return@flow
        }
       return noteList
    }

    suspend fun saveNote(note: Note) = repository.saveNote(note)

    fun updateNote(note: Note) {
        noteList.add(note)
    }
}
