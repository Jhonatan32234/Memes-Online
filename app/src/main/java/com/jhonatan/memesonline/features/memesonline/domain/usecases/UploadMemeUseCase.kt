package com.jhonatan.memesonline.features.memesonline.domain.usecases

import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository


class UploadMemeUseCase(
    private val repository: MemesRepository
) {
        suspend operator fun invoke(title: String, imageUri: String): Result<Unit> {
            return try {
                repository.uploadMeme(title, imageUri)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
