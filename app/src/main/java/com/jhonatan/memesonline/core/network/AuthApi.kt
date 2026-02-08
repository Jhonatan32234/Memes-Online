package com.jhonatan.memesonline.core.network

import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginRequest
import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: LoginRequest): Response<Unit>
}