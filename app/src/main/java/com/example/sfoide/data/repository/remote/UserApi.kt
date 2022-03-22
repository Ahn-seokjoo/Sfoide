package com.example.sfoide.data.repository.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api/")
    fun getUserList(
        @Query("seed") seed: Int,
        @Query("page") page: Int,
        @Query("results") results: Int,
    ): Single<UserDataDto>
}
