package com.itzik.notes.project.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.repositories.NoteRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private val repository: NoteRepository,
) : ViewModel() {


    suspend fun getAllNotes(): Flow<MutableList<Note>> {
        val noteList = flow {
            val updatedFlowList = repository.getAllNotes()
            if (updatedFlowList.isNotEmpty()) {
                emit(updatedFlowList)
            } else return@flow
        }
        return noteList
    }

    suspend fun saveNote(note: Note) = repository.saveNote(note)

    suspend fun deleteAllNotes() = repository.deleteAllNotes()





    suspend fun addNoteToTrashBin(notes: MutableList<Note>) {
        val deletedNotesList = emptyList<Note>().toMutableList()
        for (note in notes.iterator()) {
            note.isInTrashBin = true
            deletedNotesList.addAll(notes)
        }
        repository.saveDeletedNotesToTrashBin(deletedNotesList)
    }

    suspend fun getAllDeletedNotes(): Flow<MutableList<Note>> {
        val deletedNoteList = flow {
            val updatedList = repository.getAllDeletedNotes()
            if (updatedList.isNotEmpty()) {
                emit(updatedList)
            } else return@flow
        }
        return deletedNoteList
    }
}
