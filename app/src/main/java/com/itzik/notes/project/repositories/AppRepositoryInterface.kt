package com.itzik.notes.project.repositories

import com.itzik.notes.project.model.Note
import com.itzik.notes.project.model.User
import com.itzik.notes.project.model.WallpaperResponse
import retrofit2.Response


interface AppRepositoryInterface {
    suspend fun insertUser(user: User)
    suspend fun fetchLoggedInUsers(): MutableList<User>
    suspend fun getUserFromEmailAndPassword(email: String, password: String): User
    suspend fun getTempUserForVerification(email: String): User

    suspend fun updateUserFields(
        userId: String,
        email: String? = null,
        profileImage:String?=null,
        phoneNumber: String? = null,
        password: String? = null,
        selectedWallpaper: String?=null,
    )

    suspend fun getUserById(userId: String): User
    suspend fun updateIsLoggedIn(user: User)
    suspend fun updateViewType(userId: String, isViewGrid: Boolean)
    suspend fun fetchViewType(userId: String): Boolean
    suspend fun getWallpaperListByQuery(query: String): Response<WallpaperResponse>


    suspend fun saveNote(note: Note)
    suspend fun fetchNotes(userId: String): MutableList<Note>
    suspend fun insertSingleNoteIntoRecycleBin(note: Note)
    suspend fun setTrash(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun insertNoteListIntoRecycleBin(notes: MutableList<Note>)
    suspend fun fetchTrashedNotes(userId: String): MutableList<Note>
    suspend fun emptyTrashBin()
    suspend fun fetchStarredNotes(userId: String): MutableList<Note>
    suspend fun getSortedNotes(sortType: String, userId: String): MutableList<Note>
    suspend fun deleteNote(note: Note)
    suspend fun updateFontWeight(noteId: Int, fontWeight: Int)

}
    