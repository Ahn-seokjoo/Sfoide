package com.example.sfoide.remote

import com.example.sfoide.entities.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("api/")
    fun getUserList(@Query("results") results: Int): Call<UserData>
}
