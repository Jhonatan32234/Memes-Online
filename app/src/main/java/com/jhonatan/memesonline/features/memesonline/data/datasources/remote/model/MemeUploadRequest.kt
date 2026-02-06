package com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class MemeUploadRequest(
    @SerializedName("title") val title: String,
    @SerializedName("image_data") val imageData: String
)