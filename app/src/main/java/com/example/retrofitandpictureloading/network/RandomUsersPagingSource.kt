package com.example.retrofitandpictureloading.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitandpictureloading.data.RandomUsersRepository
import com.example.retrofitandpictureloading.model.User
import kotlinx.coroutines.flow.first

class RandomUsersPagingSource(
    private val randomUsersRepository: RandomUsersRepository
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        val pagesLoaded = randomUsersRepository.getPagesNumber()
        val forceRefresh = if (pagesLoaded < page) true else false
        val usersFromDb = randomUsersRepository.getRandomUsers(page, forceRefresh)
        return LoadResult.Page(
            data = usersFromDb,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (usersFromDb.isEmpty()) null else page + 1
        )

    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}