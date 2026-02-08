package com.jhonatan.memesonline.features.memesonline.domain.usecases

import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository

class DeleteMemeUseCase(
    private val repository: MemesRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return try {
            repository.deleteMeme(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}