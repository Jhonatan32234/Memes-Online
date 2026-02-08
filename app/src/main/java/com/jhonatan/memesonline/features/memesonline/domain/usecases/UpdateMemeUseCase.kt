package com.jhonatan.memesonline.features.memesonline.domain.usecases

import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository

class UpdateMemeUseCase(
    private val repository: MemesRepository
) {
    suspend operator fun invoke(id: String, title: String?, imageBase64: String?): Result<Unit> {
        return try {
            repository.updateMeme(id, title, imageBase64)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}