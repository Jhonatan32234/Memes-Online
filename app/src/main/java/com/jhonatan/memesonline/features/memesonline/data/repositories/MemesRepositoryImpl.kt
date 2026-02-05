package com.jhonatan.memesonline.features.memesonline.data.repositories

import com.jhonatan.memesonline.core.network.MemeApi
import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository

class MemesRepositoryImpl(
    private val api: MemeApi
) : MemesRepository {

    override suspend fun getAllMemes(): List<Meme> {
        val response = api.getMemes()
        return response.map { it.toDomain() }
    }

    override suspend fun uploadMeme(title: String, imageUrl: String) {
        println("Subiendo meme: $title con ruta $imageUrl")
    }
}