package com.example.sfoide.data.source

import com.example.sfoide.entities.UserData
import io.reactivex.rxjava3.core.Single

interface RemoteUserListDataSource {
    fun remoteGetUserList(seed: Int, page: Int): Single<UserData>
}
