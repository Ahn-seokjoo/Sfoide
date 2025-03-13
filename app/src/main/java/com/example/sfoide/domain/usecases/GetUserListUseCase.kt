package com.example.sfoide.domain.usecases

import com.example.sfoide.data.repository.remote.RemoteUserListDataSource
import com.example.sfoide.domain.entities.UserData
import com.example.sfoide.domain.entities.toUserData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val remoteUserListDataSource: RemoteUserListDataSource
) {
    operator fun invoke(seed: Int, page: Int): Single<List<UserData>> {
        return remoteUserListDataSource.remoteGetUserList(seed, page)
            .map { userDataDto ->
                userDataDto.results.map {
                    it.toUserData()
                }
            }
    }
}
