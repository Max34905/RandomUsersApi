package com.example.retrofitandpictureloading.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE page = :page")
    suspend fun getUsersByPage(page: Int): List<UserEntity>

    @Query("SELECT page FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getPagesNumber(): Int
}