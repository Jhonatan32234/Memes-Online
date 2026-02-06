package com.jhonatan.memesonline.core.network

import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeDto
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeUploadRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemeApi {
    @GET("memes")
    suspend fun getMemes(): List<MemeDto>

    @POST("memes")
    suspend fun uploadMeme(@Body request: MemeUploadRequest): Response<Unit>

}
