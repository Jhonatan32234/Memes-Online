package com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class MemeDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val imageUrl: String,
    @SerializedName("user_name") val author: String,
    @SerializedName("created_at") val date: String
)