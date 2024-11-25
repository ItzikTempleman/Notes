package com.itzik.notes_.project.viewmodels

import android.util.Log
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.model.Note.Companion.getCurrentTime
import com.itzik.notes_.project.repositories.AppRepositoryInterface
import com.itzik.notes_.project.utils.Constants.MY_BACKEND_BASE_URL
import com.itzik.notes_.project.utils.Constants.NAX_PINNED_NOTES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject
import kotlin.collections.mutableListOf


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repo: AppRepositoryInterface,
) : ViewModel() {

    private val privateNote = MutableStateFlow(Note(content = "", userId = "", fontSize = 20))
    val publicNote: StateFlow<Note> get() = privateNote

    private val privateNoteList = MutableStateFlow<MutableList<Note>>(mutableListOf())
    val publicNoteList: StateFlow<MutableList<Note>> get() = privateNoteList

    private val privatePinnedNoteList = MutableStateFlow<MutableList<Note>>(mutableListOf())
    val publicPinnedNoteList: StateFlow<MutableList<Note>> get() = privatePinnedNoteList

    private val privateStarredNoteList = MutableStateFlow<MutableList<Note>>(mutableListOf())
    val publicStarredNoteList: StateFlow<MutableList<Note>> get() = privateStarredNoteList

    private var privatePinStateMap = MutableStateFlow(mapOf<Int, Boolean>())
    val publicPinStateMap: MutableStateFlow<Map<Int, Boolean>> get() = privatePinStateMap

    private var privateStarStateMap = MutableStateFlow(mapOf<Int, Boolean>())
    val publicStarStateMap: MutableStateFlow<Map<Int, Boolean>> get() = privateStarStateMap

    private val privateDeletedNoteList = MutableStateFlow<MutableList<Note>>(mutableListOf())
    val publicDeletedNoteList: StateFlow<MutableList<Note>> get() = privateDeletedNoteList


    var userId = ""

    init {
        viewModelScope.launch {
            fetchCurrentLoggedInUserId()
        }
    }

    private suspend fun fetchCurrentLoggedInUserId() {
        val users = repo.fetchLoggedInUsers()
        val loggedInUser = users.firstOrNull { it.isLoggedIn }
        userId = loggedInUser?.userId ?: ""
    }

    suspend fun updateSelectedNoteContent(
        newChar: String,
        userId: String,
        noteId: Int? = 0,
        isPinned: Boolean,
        isStarred: Boolean,
        fontSize: Int,
        fontColor: Int,
        fontWeight: Int
    ) {

        privateNote.value = privateNote.value.copy(
            fontSize = fontSize,
            userId = userId,
            fontColor = fontColor,
            isPinned = isPinned,
            isStarred = isStarred,
            content = newChar,
            time = getCurrentTime(),
            fontWeight = fontWeight
        )

        if (noteId != null) {
            privateNote.value.noteId = noteId
        }
        repo.updateNote(privateNote.value)
    }

    suspend fun saveNote(note: Note) {
        if (userId.isEmpty()) {
            fetchCurrentLoggedInUserId()
        }

        // Save the note locally and retrieve the generated noteId
        val noteId = if (note.noteId == 0) {
            // Save the note locally (Room DB) and get the generated noteId
            repo.saveNote(note.copy(userId = userId)) // Save locally (Room DB)
            val generatedNote = repo.fetchNotes(userId).last()  // Fetch the last note added (as it's newly added)
            generatedNote.noteId // Return the generated noteId
        } else {
            // If the note already exists, use the existing noteId
            note.noteId
        }

        // Create the noteToSave object with the correct noteId
        val noteToSave = note.copy(userId = userId, noteId = noteId)

        try {
            // Send the note to the backend
            val existingNoteInBackend = repo.fetchNotes(userId).find { it.noteId == noteToSave.noteId }

            if (existingNoteInBackend == null) {
                // Insert the note into the backend if it doesn't exist
                repo.insertNoteIntoBackEnd(noteToSave)
                Log.d("TAG", "New note posted successfully with ID: ${noteToSave.noteId}")
            } else {
                // Update the existing note in the backend
                //repo.updateNoteInBackEnd(noteToSave)
                Log.d("TAG", "Existing note updated with ID: ${noteToSave.noteId}")
            }
        } catch (httpE: HttpException) {
            Log.e("TAG", "HTTP error: ${httpE.code()} - ${httpE.response()?.errorBody()?.string()}")
        } catch (e: Exception) {
            Log.e("TAG", "Unexpected error: ${e.localizedMessage}")
        }

        // Fetch the latest notes after saving the note to the backend
        fetchNotesForUser(userId)
    }
//    suspend fun saveNote(note: Note) {
//        if (userId.isEmpty()) {
//            fetchCurrentLoggedInUserId()
//        }
//
//        // Save the note locally and fetch the generated ID
//        val noteId = if (note.noteId == 0) {
//            val generatedId = repo.saveNote(note) // Local save for Room DB
//            generatedId
//        } else {
//            note.noteId
//        }
//
//        val noteToSave = note.copy(userId = userId, noteId = noteId as Int)
//
//        try {
//            // Send the note to the backend
//            repo.insertNoteIntoBackEnd(noteToSave)
//            Log.d("TAG", "Note posted successfully with ID: $noteId")
//        } catch (httpE: HttpException) {
//            Log.e("TAG", "HTTP error: ${httpE.code()} - ${httpE.response()?.errorBody()?.string()}")
//        } catch (e: Exception) {
//            Log.e("TAG", "Unexpected error: ${e.localizedMessage}")
//        }
//
//        fetchNotesForUser(userId)
//    }


    suspend fun fetchNotesForUser(userId: String) {
        if (userId.isNotEmpty()) {
            val notes = repo.fetchNotes(userId)

            val sortedNotes = notes.sortedWith(compareByDescending { it.isPinned })

            privateNoteList.value = sortedNotes.toMutableList()

            val pinMap = sortedNotes.associate { it.noteId to it.isPinned }
            val starMap = sortedNotes.associate { it.noteId to it.isStarred }
            privatePinStateMap.value = pinMap
            privateStarStateMap.value = starMap
        } else {
            privateNoteList.value = mutableListOf()
        }
    }

    fun fetchOnlineNotes(userId: String): Flow<MutableList<Note>> {
        val notes = flow {
            val response = repo.getNotesFromBackEnd(userId)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(responseBody)
                } else {
                    Log.d("TAG", response.message())
                    emit(mutableListOf())
                }
                return@flow
            } else {
                Log.d("TAG", response.message())
                emit(mutableListOf())
            }
            return@flow
        }
        return notes
    }

    suspend fun setTrash(note: Note) {
        note.isInTrash = true
        note.isStarred = false
        note.isPinned = false
        repo.setTrash(note)
        repo.insertSingleNoteIntoRecycleBin(note)
        fetchNotesForUser(userId)
        fetchDeletedNotes()
    }

    fun emptyTrashBin() = viewModelScope.launch {
        repo.emptyTrashBin()
        fetchDeletedNotes()
    }

    suspend fun getSortedNotes(sortType: String) {
        val sortedNotes = repo.getSortedNotes(sortType, userId)
        privateNoteList.value = sortedNotes.toMutableList()
    }


    fun retrieveNote(note: Note) = viewModelScope.launch {
        note.isInTrash = false
        repo.updateNote(note)
        fetchNotesForUser(userId)
        fetchDeletedNotes()
    }

    fun fontWeightToInt(fontWeight: FontWeight): Int {
        return fontWeight.weight
    }

    fun intToFontWeight(fontWeightInt: Int): FontWeight {
        return when (fontWeightInt) {
            700 -> FontWeight.Bold
            else -> FontWeight.Normal
        }
    }


    fun updateUserIdForNewLogin() {
        viewModelScope.launch {
            fetchCurrentLoggedInUserId()
        }
    }

    suspend fun deleteNotePermanently(note: Note) {
        repo.deleteNote(note)
        fetchDeletedNotes()
    }

    suspend fun fetchDeletedNotes() {
        val notes = repo.fetchTrashedNotes(userId)
        privateDeletedNoteList.value = notes
    }

    fun fetchStarredNotes() = viewModelScope.launch {
        val starredNotes = repo.fetchStarredNotes(userId = userId)
        privateStarredNoteList.value = starredNotes

    }


    suspend fun toggleStarredButton(note: Note) {
        val updatedNote = note.copy(isStarred = !note.isStarred)
        repo.updateNote(updatedNote)
        fetchNotesForUser(userId)
        privateStarStateMap.value = privateStarStateMap.value.toMutableMap().apply {
            put(updatedNote.noteId, updatedNote.isStarred)
        }
        fetchStarredNotes()
    }

    suspend fun togglePinButton(note: Note) {
        if (!note.isPinned && privatePinnedNoteList.value.size >= NAX_PINNED_NOTES) return

        note.isPinned = !note.isPinned
        privatePinStateMap.value = privatePinStateMap.value.toMutableMap().apply {
            put(note.noteId, note.isPinned)
        }
        updatePinnedNotes(note, note.isPinned)
        repo.updateNote(note)
        fetchNotesForUser(userId)
    }

    fun unLikeNote(note: Note) {
        viewModelScope.launch {
            val updatedNote = note.copy(isStarred = false)
            repo.updateNote(updatedNote)
            fetchNotesForUser(userId)
            privateStarStateMap.value = privateStarStateMap.value.toMutableMap().apply {
                put(updatedNote.noteId, updatedNote.isStarred)
            }
            fetchStarredNotes()

        }
    }

    private fun updatePinnedNotes(note: Note, isPinned: Boolean) {
        if (isPinned) {
            privatePinnedNoteList.value.add(note)
            privateNoteList.value.remove(note)
        } else {
            privatePinnedNoteList.value.remove(note)
            privateNoteList.value.add(note)
        }
    }

    fun clearAllNoteList() {
        privateNoteList.value = emptyList<Note>().toMutableList()
        privateStarredNoteList.value = emptyList<Note>().toMutableList()
        privateDeletedNoteList.value = emptyList<Note>().toMutableList()
        privatePinnedNoteList.value = emptyList<Note>().toMutableList()
    }

}

