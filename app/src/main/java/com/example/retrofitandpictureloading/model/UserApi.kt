package com.example.retrofitandpictureloading.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results") val result: List<UserApi>,
    @SerializedName("info") val info: Info
)

data class UserApi(
    @SerializedName("gender") val gender: String = "",
    @SerializedName("name") val name: Name,
    @SerializedName("picture") val picture: Picture
)

data class Name(
    @SerializedName("title") val title: String = "",
    @SerializedName("first") val firstName: String = "",
    @SerializedName("last") val lastName: String = ""
)

data class Picture(
    @SerializedName("large") val large: String = "",
    @SerializedName("medium") val medium: String = "",
    @SerializedName("thumbnail") val thumbnail: String = ""
)

data class Info (
    @SerializedName("seed") val seed: String,
    @SerializedName("results") val results: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("version") val version: String
)
