package com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class MemeDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_data") val imageData: String,
    @SerializedName("author_id") val authorId: Int,
    @SerializedName("created_at") val date: String
)