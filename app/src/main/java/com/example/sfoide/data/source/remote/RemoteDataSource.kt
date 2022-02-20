package com.example.sfoide.data.source.remote

import com.example.sfoide.data.source.UserListDataSource
import com.example.sfoide.ui.userlist.MainActivity

class RemoteDataSource : UserListDataSource {
    // 서버랑 통신을 해서 유저의 리스트를 가져옵니다.
    override fun remoteGetUserList(seed: Int, page: Int) =
        NetworkManager.UserApi.getUserList(page, MainActivity.USER_COUNT, seed)
}

