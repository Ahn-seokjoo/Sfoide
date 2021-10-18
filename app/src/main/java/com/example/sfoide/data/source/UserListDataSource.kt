package com.example.sfoide.data.source

import kotlinx.coroutines.Job

interface UserListDataSource {
    // 유저 데이터 요청
    fun remoteGetUserList(offset: Int? = null): Job

    // 데이터 초기화
    fun clearUserListData()
}
