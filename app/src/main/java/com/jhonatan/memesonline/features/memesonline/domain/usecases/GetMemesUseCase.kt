package com.jhonatan.memesonline.features.memesonline.domain.usecases

import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository

class GetMemesUseCase(
    private val repository: MemesRepository
) {

    suspend operator fun invoke(): Result<List<Meme>> {
        return try {
            val memes = repository.getAllMemes()
            Result.success(memes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}