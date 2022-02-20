package com.example.sfoide.ui.userlist

import com.example.sfoide.data.source.remote.RemoteDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {
    // 뷰, 모델 연결 + 비즈니스 로직
    private val userListRemoteDataSource: RemoteDataSource = RemoteDataSource()

    override fun loadDataList(seed: Int, page: Int) {
        userListRemoteDataSource.remoteGetUserList(seed, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.results
            }.subscribe({
                view.showUserList(it)
            }) {
                Timber.d("${it.message} 에러!")
            }
    }
}
