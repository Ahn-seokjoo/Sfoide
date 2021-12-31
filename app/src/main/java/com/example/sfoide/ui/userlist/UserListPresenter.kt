package com.example.sfoide.ui.userlist

import com.example.sfoide.data.source.remote.RemoteDataSource
import com.example.sfoide.entities.UserData

class UserListPresenter :
    UserListContract.Presenter {
    // 뷰, 모델 연결 + 비즈니스 로직
    private val userListRemoteDataSource: RemoteDataSource = RemoteDataSource()

    override suspend fun loadDataList(seed: Int, page: Int): List<UserData.Result> {
        return userListRemoteDataSource.remoteGetUserList(seed, page)
    }
}
