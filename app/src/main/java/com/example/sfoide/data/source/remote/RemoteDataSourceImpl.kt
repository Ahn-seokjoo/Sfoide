package com.example.sfoide.data.source.remote

import com.example.sfoide.data.source.RemoteUserListDataSource
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.userlist.MainActivity
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : RemoteUserListDataSource {
    // 서버랑 통신을 해서 유저의 리스트를 가져옵니다.
    override suspend fun remoteGetUserList(seed: Int, page: Int): List<UserData.Result> {
        // 서버를 통신해서
        val userListData = userApi.getUserList(page, MainActivity.USER_COUNT, seed)

        // 받은 데이터를 리턴
        return userListData.body()?.results ?: emptyList()
    }

}

