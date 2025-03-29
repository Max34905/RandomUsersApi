package com.example.retrofitandpictureloading.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.retrofitandpictureloading.data.RandomUsersRepository
import com.example.retrofitandpictureloading.model.User
import com.example.retrofitandpictureloading.network.RandomUsersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val randomUsersRepository: RandomUsersRepository
) : ViewModel() {

    val homeScreenUiState: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RandomUsersPagingSource(randomUsersRepository) }
    ).flow.cachedIn(viewModelScope)
}