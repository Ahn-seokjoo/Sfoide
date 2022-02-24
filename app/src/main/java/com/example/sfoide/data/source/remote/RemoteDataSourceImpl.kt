package com.example.sfoide.data.source.remote

import com.example.sfoide.data.source.RemoteUserListDataSource
import com.example.sfoide.entities.UserData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : RemoteUserListDataSource {
    override fun remoteGetUserList(seed: Int, page: Int): Single<UserData> {
        return userApi.getUserList(page, USER_COUNT, seed)
    }

    companion object {
        const val USER_COUNT = 40
    }
}

