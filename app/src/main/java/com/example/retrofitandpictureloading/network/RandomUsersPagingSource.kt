package com.example.retrofitandpictureloading.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitandpictureloading.data.RandomUsersRepository
import com.example.retrofitandpictureloading.model.User

class RandomUsersPagingSource(
    private val randomUsersRepository: RandomUsersRepository
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPage = params.key ?: 1
        val pagesLoaded = randomUsersRepository.getPagesNumber()
        val forceRefresh = if (pagesLoaded < currentPage) true else false
        val usersFromDb = randomUsersRepository.getRandomUsers(currentPage, forceRefresh)
        return LoadResult.Page(
            data = usersFromDb,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (usersFromDb.isEmpty()) null else currentPage + 1
        )

    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}