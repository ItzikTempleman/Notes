package com.itzik.notes_.project.viewmodels

import android.util.Log
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.itzik.notes_.project.repositories.AppRepositoryInterface
import com.itzik.notes_.project.utils.Constants.NAX_PINNED_NOTES
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.model.Note.Companion.getCurrentTime
import com.itzik.notes_.project.model.WallpaperResponse
import com.itzik.notes_.project.utils.Constants.MY_BACKEND_BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

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

    suspend fun saveNote(note: Note) {
        if (userId.isEmpty()) {
            fetchCurrentLoggedInUserId()
        }

        val noteToSave = note.copy(userId = userId)

        val noteList = repo.fetchNotes(userId)
        val matchingNoteToPreviousVersion = noteList.find {
            it.noteId == note.noteId
        }

        if (matchingNoteToPreviousVersion == null) {
            repo.saveNote(noteToSave)
            try {
                val requestBody = Gson().toJson(noteToSave)
                val url = "${MY_BACKEND_BASE_URL}api/notes"
                Log.d(
                    "TAG",
                    "Request URL: $url, and request Body: $requestBody, Note posted successfully"
                )
                repo.insertNoteIntoBackEnd(noteToSave)
            } catch (httpE: HttpException) {
                Log.e(
                    "TAG",
                    "HTTP error: ${httpE.code()} - ${httpE.response()?.errorBody()?.string()}"
                )
            } catch (e: Exception) {
                Log.e("TAG", "Unexpected error: ${e.localizedMessage}")
            }
        } else {
            updateSelectedNoteContent(
                userId = note.userId,
                newChar = note.content,
                isPinned = note.isPinned,
                isStarred = note.isStarred,
                fontSize = note.fontSize,
                fontColor = note.fontColor,
                fontWeight = note.fontWeight
            )
        }
        fetchNotesForUser(userId)
    }

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

