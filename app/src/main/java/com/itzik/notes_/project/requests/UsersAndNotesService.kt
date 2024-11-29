package com.itzik.notes_.project.requests

import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersAndNotesService {

    @POST("api/users")
    suspend fun postAUser(@Body user: User)

    @GET("api/users")
    suspend fun getUsersFromBackEnd(): Response<List<User>>

    @GET("api/authenticate")
    suspend fun getUserFromUserNameAndPasswordFromOnline(
        @Query("email") userName: String,
        @Query("password") password: String
    ): Response<User>


    @POST("api/notes/user/{userId}")
    suspend fun postNoteForUser(
        @Path("userId") userId: String,
        @Body note: Note
    ):Response<Note>


    @GET("api/notes/user/{userId}")
    suspend fun getAllNotesForUser(
        @Path("userId") userId: String
    ): Response<MutableList<Note>>

}