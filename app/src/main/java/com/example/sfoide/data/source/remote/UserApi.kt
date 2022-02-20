package com.example.sfoide.data.source.remote

import com.example.sfoide.entities.UserData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api/")
    fun getUserList(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: Int,
    ): Single<UserData>
}
