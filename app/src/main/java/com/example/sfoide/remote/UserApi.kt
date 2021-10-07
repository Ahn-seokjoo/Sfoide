package com.example.sfoide.remote

import com.example.sfoide.entities.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api/")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: Int,
    ): Response<UserData>
}
