package com.example.sfoide.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.sfoide.data.PagingUserDataSource

class UserPagingViewModel : ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = 5),
        pagingSourceFactory = { PagingUserDataSource() }
    ).flow
        .cachedIn(viewModelScope)
}
