package com.example.sfoide.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sfoide.domain.entities.UserData
import com.example.sfoide.domain.usecases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val getUserListUseCase: GetUserListUseCase) : ViewModel() {
    private val _progressBar = MutableLiveData<Boolean>(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _allUserList = MutableLiveData<List<UserData>>(emptyList())
    val allUserList: LiveData<List<UserData>> = _allUserList

    private val userList: MutableList<UserData> = mutableListOf()

    fun loadDataList(seed: Int, page: Int) {
        getUserListUseCase(seed, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _progressBar.value = true
            }
            .doOnSuccess {
                _progressBar.value = false
            }
            .subscribe({ userDataList ->
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

