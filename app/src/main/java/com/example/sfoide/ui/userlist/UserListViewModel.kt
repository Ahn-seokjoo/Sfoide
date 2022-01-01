package com.example.sfoide.ui.userlist

import androidx.lifecycle.ViewModel
import com.example.sfoide.data.source.RemoteUserListDataSource
import com.example.sfoide.entities.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRemoteDataSource: RemoteUserListDataSource) : ViewModel() {
    private val _userList = MutableStateFlow<List<UserData.Result>>(emptyList())
    val userList: StateFlow<List<UserData.Result>> = _userList // 가져온 리스트

    private val _allUserList: MutableList<UserData.Result> = mutableListOf()
    val allUserList: List<UserData.Result> = _allUserList

    suspend fun loadDataList(seed: Int, page: Int) {
        _userList.value = userListRemoteDataSource.remoteGetUserList(seed, page)
        if (page == 1) {
            _allUserList.clear()
        }
        _allUserList.addAll(_userList.value)
    }
}
