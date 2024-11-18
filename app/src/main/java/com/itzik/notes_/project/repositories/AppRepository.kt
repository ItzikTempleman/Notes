package com.itzik.notes_.project.repositories

import com.itzik.notes_.project.data.NoteDao
import com.itzik.notes_.project.data.UserDao
import com.itzik.notes_.project.model.User
import com.itzik.notes_.project.model.WallpaperResponse
import com.itzik.notes_.project.requests.WallpaperService
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.requests.UsersAndNotesService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class AppRepository @Inject constructor(
    @Named("user_dao")
    @Singleton
    private val userDao: UserDao,
    @Named("note_dao")
    @Singleton
    private val noteDao: NoteDao,
    @Named("wallpaper_service")
    @Singleton
    private val wallpaperService: WallpaperService,
    @Named("my_backend_service")
    @Singleton
    private val usersAndNotesService: UsersAndNotesService,
) : AppRepositoryInterface {

    override suspend fun insertUserIntoBackEnd(user: User) = usersAndNotesService.postAUser(user)
    override suspend fun getUsersFromBackEnd(): Response<List<User>> = usersAndNotesService.getUsersFromBackEnd()
    override suspend fun insertNoteIntoBackEnd(note: Note) = usersAndNotesService.postANoteForUser(note)
    override suspend fun getNotesFromBackEnd(userId: String): Response<List<Note>> = usersAndNotesService.getAllNotesForUser(userId)


    override suspend fun insertUser(user: User) = userDao.insertUser(user)
    override suspend fun fetchLoggedInUsers() = userDao.fetchLoggedInUsers()
    override suspend fun getUserFromEmailAndPassword(email: String, password: String) =
        userDao.getUserFromEmailAndPassword(email, password)

    override suspend fun getTempUserForVerification(email: String) =
        userDao.getTempUserForVerification(email)

    override suspend fun updateIsLoggedIn(user: User) = userDao.updateIsLoggedIn(user)

    override suspend fun updateUserFields(
        userId: String,
        email: String?,
        profileImage: String?,
        phoneNumber: String?,
        password: String?,
        selectedWallpaper: String?
    ) = userDao.updateUserFields(
        userId,
        email,
        profileImage,
        phoneNumber,
        password,
        selectedWallpaper
    )

    override suspend fun getWallpaperListByQuery(query: String): Response<WallpaperResponse> =
        wallpaperService.getWallpaperListBySearchQuery(searchQuery = query)

    override suspend fun getUserById(userId: String): User = userDao.getUserById(userId)
    override suspend fun updateViewType(userId: String, isViewGrid: Boolean) =
        userDao.updateViewType(userId, isViewGrid)

    override suspend fun fetchViewType(userId: String): Boolean = userDao.fetchViewType(userId)

    override suspend fun saveNote(note: Note) = noteDao.saveNote(note)
    override suspend fun fetchNotes(userId: String) = noteDao.fetchNotes(userId)
    override suspend fun setTrash(note: Note) = noteDao.setTrash(note)
    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    override suspend fun insertSingleNoteIntoRecycleBin(note: Note) =
        noteDao.insertSingleNoteIntoRecycleBin(note)

    override suspend fun insertNoteListIntoRecycleBin(notes: MutableList<Note>) =
        noteDao.insertNoteListIntoRecycleBin(notes)

    override suspend fun fetchTrashedNotes(userId: String) = noteDao.fetchTrashedNotes(userId)
    override suspend fun emptyTrashBin() = noteDao.emptyTrashBin()
    override suspend fun fetchStarredNotes(userId: String) = noteDao.fetchStarredNotes(userId)
    override suspend fun getSortedNotes(sortType: String, userId: String): MutableList<Note> {
        return when (sortType) {
            "Sort alphabetically" -> noteDao.fetchNotesSortedAlphabetically(userId)
            "Sort by date modified" -> noteDao.fetchNotesSortedByDateModified(userId)
            else -> noteDao.fetchNotes(userId)
        }
    }

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    override suspend fun updateFontWeight(
        noteId: Int,
        fontWeight: Int
    ) = noteDao.updateFontWeight(noteId, fontWeight)


}