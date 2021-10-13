package com.example.sfoide.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sfoide.entities.UserData
import com.example.sfoide.remote.NetworkManager
import com.example.sfoide.ui.userlist.MainActivity
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class PagingUserDataSource : PagingSource<Int, UserData.Result>() {
    private var seedData = Random().nextInt()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData.Result> {
        return try {
            var nextPageNumber = params.key ?: MainActivity.FIRST_PAGE
            val responses = NetworkManager.UserApi.getUserList(nextPageNumber, MainActivity.USER_COUNT, seedData)

            LoadResult.Page(
                data = responses.body()!!.results,
                prevKey = if (nextPageNumber == MainActivity.FIRST_PAGE) null else nextPageNumber - 1,
                nextKey = nextPageNumber.plus(1))
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserData.Result>): Int? {
        return state.anchorPosition?.let { it ->
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
