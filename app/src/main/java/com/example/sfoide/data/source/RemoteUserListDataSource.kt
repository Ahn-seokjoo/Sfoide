package com.example.sfoide.data.source

import com.example.sfoide.entities.UserData

interface RemoteUserListDataSource {
    // 유저 데이터 요청
    suspend fun remoteGetUserList(seed: Int, page: Int): List<UserData.Result>
}
