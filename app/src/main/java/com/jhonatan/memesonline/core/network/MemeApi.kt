package com.jhonatan.memesonline.core.network

import retrofit2.http.GET
import retrofit2.http.POST

interface MemeApi {
    @GET("memes")
    suspend fun getMemes(): List<MemeDto>

    @POST("memes/upload")
    suspend fun uploadMeme(@Body meme: MemeDto): Response<Unit>
}