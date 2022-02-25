package com.example.sfoide.data.repository.remote

import io.reactivex.rxjava3.core.Single

interface RemoteUserListDataSource {
    fun remoteGetUserList(seed: Int, page: Int): Single<UserDataDto>
}
