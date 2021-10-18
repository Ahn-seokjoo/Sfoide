package com.example.sfoide.data.source.remote

import com.example.sfoide.data.source.UserListDataSource
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.userlist.MainActivity
import com.example.sfoide.ui.userlist.UserListPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RemoteDataSource(private val presenter: UserListPresenter) : UserListDataSource {
    private val resultUserDataList = mutableListOf<UserData.Result>()
    private var seedData = Random().nextInt()

    override fun remoteGetUserList(offset: Int?) =
        CoroutineScope(Dispatchers.IO).launch {
            when (offset) {
                null -> {
                    seedData = Random().nextInt()
                    val userListData = NetworkManager.UserApi.getUserList(MainActivity.FIRST_PAGE, MainActivity.USER_COUNT, seedData)
                    userListData.body()?.results?.forEach { resultUserDataList.add(it) }
                    withContext(Dispatchers.Main) {
                        presenter.fetchUserListData(resultUserDataList.toList())
                    }
                }
                else -> {
                    val userListData = NetworkManager.UserApi.getUserList(offset, MainActivity.USER_COUNT, seedData)
                    userListData.body()?.results?.forEach { resultUserDataList.add(it) }
                    withContext(Dispatchers.Main) {
                        presenter.fetchUserListData(resultUserDataList.toList())
                    }
                }
            }
        }

    override fun clearUserListData() {
        resultUserDataList.clear()
        presenter.fetchUserListData(resultUserDataList.toList())
    }

}

