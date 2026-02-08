package com.jhonatan.memesonline.features.auth.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user_id") val userId: String?,
    @SerializedName("user_name") val userName: String
)