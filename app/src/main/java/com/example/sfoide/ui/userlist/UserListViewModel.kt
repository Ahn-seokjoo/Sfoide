package com.example.sfoide.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sfoide.data.source.RemoteUserListDataSource
import com.example.sfoide.entities.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRemoteDataSource: RemoteUserListDataSource) : ViewModel() {
    private val _allUserList = MutableLiveData<List<UserData.Result>>(emptyList())
    val allUserList: LiveData<List<UserData.Result>> = _allUserList

    private val _userList: MutableList<UserData.Result> = mutableListOf()
    val userList: List<UserData.Result> = _userList

    fun loadDataList(seed: Int, page: Int) {
        viewModelScope.launch {
            if (page == 1) {
                _userList.clear()
            }
            _userList.addAll(userListRemoteDataSource.remoteGetUserList(seed, page))
            _allUserList.value = _userList
        }
    }
}
