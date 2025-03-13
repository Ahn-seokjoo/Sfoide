package com.example.sfoide.data.source

import com.example.sfoide.entities.UserData
import io.reactivex.rxjava3.core.Single

interface UserListDataSource {
    // 유저 데이터 요청
    fun remoteGetUserList(seed: Int, page: Int): Single<UserData>
}
