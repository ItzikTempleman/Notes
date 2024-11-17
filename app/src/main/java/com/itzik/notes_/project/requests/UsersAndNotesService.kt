package com.itzik.notes_.project.requests

import com.itzik.notes_.project.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersAndNotesService {


    @POST("/api/users")
    suspend fun postAUser(@Body user: User)


    @GET("/api")
    suspend fun getUsersFromBackEnd() : Response<List<User>>
}