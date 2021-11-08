package com.example.sfoide.data.source.remote

import com.example.sfoide.data.source.UserListDataSource
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.userlist.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RemoteDataSource : UserListDataSource {
    private val resultUserDataList = mutableListOf<UserData.Result>()
    private var seedData: Int = 0

    override fun remoteGetUserList(offset: Int?, fetchUserList: (List<UserData.Result>) -> Unit) =
        CoroutineScope(Dispatchers.IO).launch {
            when (offset) {
                null -> {
                    seedData = Random().nextInt()
                    val userListData = NetworkManager.UserApi.getUserList(MainActivity.FIRST_PAGE, MainActivity.USER_COUNT, seedData)
                    userListData.body()?.results?.forEach { resultUserDataList.add(it) }
                    withContext(Dispatchers.Main) {
                        fetchUserList(resultUserDataList.toList())
                    }
                }
                else -> {
                    val userListData = NetworkManager.UserApi.getUserList(offset, MainActivity.USER_COUNT, seedData)
                    userListData.body()?.results?.forEach { resultUserDataList.add(it) }
                    withContext(Dispatchers.Main) {
                        fetchUserList(resultUserDataList.toList())
                    }
                }
            }
        }

    override fun clearUserListData(fetchUserList: (List<UserData.Result>) -> Unit) {
        resultUserDataList.clear()
        fetchUserList(resultUserDataList.toList())
    }

}

