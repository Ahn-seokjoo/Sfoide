package com.example.sfoide.ui.userlist

import com.example.sfoide.data.source.remote.RemoteDataSource
import com.example.sfoide.entities.UserData

class UserListPresenter :
    UserListContract.Presenter {

    private var userListView: UserListContract.View? = null
    private val userListRemoteDataSource: RemoteDataSource = RemoteDataSource()

    fun takeView(view: UserListContract.View) {
        userListView = view
    }

    override fun loadFirstDataList() {
        userListRemoteDataSource.remoteGetUserList(null, ::fetchUserListData)
    }

    override fun loadNextDataFromApi(offset: Int) {
        userListRemoteDataSource.remoteGetUserList(offset, ::fetchUserListData)
    }

    override fun fetchUserListData(userItemList: List<UserData.Result>) {
        userListView?.submitList(userItemList)
    }

    override fun clearUserListData() {
        userListRemoteDataSource.clearUserListData(::fetchUserListData)
    }


}
