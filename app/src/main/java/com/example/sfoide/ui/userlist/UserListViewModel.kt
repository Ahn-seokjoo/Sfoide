package com.example.sfoide.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sfoide.data.source.RemoteUserListDataSource
import com.example.sfoide.entities.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRemoteDataSource: RemoteUserListDataSource) : ViewModel() {
    private val _allUserList = MutableLiveData<List<UserData.Result>>(emptyList())
    val allUserList: LiveData<List<UserData.Result>> = _allUserList

    private val _progressBar = MutableLiveData<Boolean>(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val userList: MutableList<UserData.Result> = mutableListOf()

    fun loadDataList(seed: Int, page: Int) {
        userListRemoteDataSource.remoteGetUserList(seed, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _progressBar.value = true
            }
            .doOnSuccess {
                _progressBar.value = false
            }
            .map {
                it.results
            }.subscribe({ userDataList ->
                if (page == 1) {
                    userList.clear()
                }
                userList.addAll(userDataList)
                _allUserList.value = userList
            }) {
                Timber.d(it.message)
            }

    }
}

