package com.jhonatan.memesonline.core.network

import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginRequest
import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginResponse
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeDto
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeUpdateRequest
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeUploadRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemeApi {
    @GET("memes")
    suspend fun getMemes(): List<MemeDto>

    @POST("memes")
    suspend fun uploadMeme(@Body request: MemeUploadRequest): Response<Unit>

    @PUT("memes/{id}")
    suspend fun updateMeme(@Path("id") id: String, @Body request: MemeUpdateRequest): Response<Unit>

    @DELETE("memes/{id}")
    suspend fun deleteMeme(@Path("id") id: String): Response<Unit>
}
