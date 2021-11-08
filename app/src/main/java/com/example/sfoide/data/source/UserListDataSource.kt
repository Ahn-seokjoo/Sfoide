package com.example.sfoide.data.source

import com.example.sfoide.entities.UserData
import kotlinx.coroutines.Job

interface UserListDataSource {
    // 유저 데이터 요청
    fun remoteGetUserList(offset: Int? = null, fetchUserList: (List<UserData.Result>) -> Unit): Job

    // 데이터 초기화
    fun clearUserListData(fetchUserList: (List<UserData.Result>) -> Unit)
}
