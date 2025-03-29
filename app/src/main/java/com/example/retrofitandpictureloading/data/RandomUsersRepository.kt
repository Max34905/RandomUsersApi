package com.example.retrofitandpictureloading.data

import com.example.retrofitandpictureloading.db.UserDao
import com.example.retrofitandpictureloading.db.UserEntity
import com.example.retrofitandpictureloading.model.ApiResponse
import com.example.retrofitandpictureloading.model.User
import com.example.retrofitandpictureloading.network.RandomUsersApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RandomUsersRepository {
    suspend fun getRandomUsers(page: Int, forceRefresh: Boolean): List<User>
    suspend fun getPagesNumber(): Int
}

class NetworkRandomUsersRepository(
    private val randomUsersApiService: RandomUsersApiService,
    private val userDao: UserDao
): RandomUsersRepository {
    override suspend fun getRandomUsers(page: Int, forceRefresh: Boolean): List<User> {
        val usersFromDb = userDao.getUsersByPage(page)
        return if (usersFromDb.isNotEmpty() && !forceRefresh) {
            usersFromDb.map { entity ->
                User(
                    gender = entity.gender,
                    name = entity.name,
                    picture = entity.picture
                )
            }
        } else {
            val response = randomUsersApiService.getUsers(page)
            saveUsersInDatabase(response)
            userDao.getUsersByPage(page).map { entity ->
                User(
                    gender = entity.gender,
                    name = entity.name,
                    picture = entity.picture
                )
            }
        }
    }

    private suspend fun saveUsersInDatabase(apiResponse: ApiResponse) {
        val users = apiResponse.result
        val userEntities = users.map { user ->
            UserEntity(
                gender = user.gender,
                name = "${user.name.title} ${user.name.firstName} ${user.name.lastName}",
                picture = user.picture.large,
                page = apiResponse.info.page
            )
        }
        userDao.upsertUsers(userEntities)
    }

    override suspend fun getPagesNumber(): Int {
        return userDao.getPagesNumber()
    }
}