package com.example.sfoide.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.sfoide.data.PagingUserDataSource
import java.util.*

class UserPagingViewModel : ViewModel() {
    var seedData = 0
    val flow = Pager(
        PagingConfig(pageSize = 5),
        pagingSourceFactory = { PagingUserDataSource(seedData) }
    ).flow
        .cachedIn(viewModelScope)

    fun doRefresh() {
        seedData = Random().nextInt()
    }
}
