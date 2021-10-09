package com.example.sfoide.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sfoide.entities.UserData
import com.example.sfoide.remote.NetworkManager
import retrofit2.HttpException
import java.io.IOException

class PagingUserDataSource(private val seedData: Int) : PagingSource<Int, UserData.Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData.Result> {
        return try {
            var nextPageNumber = params.key ?: FIRST_PAGE
            val responses = NetworkManager.UserApi.getUserList(nextPageNumber, USER_COUNT, seedData)

            LoadResult.Page(
                data = responses.body()!!.results,
                prevKey = if (nextPageNumber == FIRST_PAGE) null else nextPageNumber - 1,
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

    companion object {
        const val FIRST_PAGE = 1
        const val USER_COUNT = 100
    }
}
