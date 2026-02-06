package com.jhonatan.memesonline.features.memesonline.domain.repositories

import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme

interface MemesRepository {
    suspend fun getAllMemes(): List<Meme>

    suspend fun uploadMeme(title: String, imageUrl: String): Result<Unit>
}