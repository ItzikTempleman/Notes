package com.itzik.notes_.project.requests

import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersAndNotesService {

    @POST("api/users")
    suspend fun postAUser(@Body user: User)

    @GET("api/users")
    suspend fun getUsersFromBackEnd(): Response<List<User>>



    @POST("/api/notes")
    suspend fun postANoteForUser(@Body note: Note)


    @GET("/api/notes/user/{userId}")
    suspend fun getAllNotesForUser(
        @Path("userId") userId: String
    ): Response<MutableList<Note>>

}