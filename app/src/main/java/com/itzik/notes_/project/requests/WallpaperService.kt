package com.itzik.notes_.project.requests

import com.itzik.notes_.project.model.WallpaperResponse
import com.itzik.notes_.project.utils.Constants.API_KEY_VALUE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLEncoder

interface WallpaperService {

    @GET("/api/")
    suspend fun getWallpaperListBySearchQuery(
        @Query("key") apiKey: String = URLEncoder.encode(API_KEY_VALUE),
        @Query("q") searchQuery: String,
        @Query("image_type") imageType: String = "photo"
    ): Response<WallpaperResponse>

}