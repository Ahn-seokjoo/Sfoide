package com.example.sfoide.data.repository.remote

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteUserListDataSourceImpl @Inject constructor(private val userApi: UserApi) : RemoteUserListDataSource {
    override fun remoteGetUserList(seed: Int, page: Int): Single<UserDataDto> {
        return userApi.getUserList(seed, page, USER_COUNT)
    }

    companion object {
        const val USER_COUNT = 40
    }
}

