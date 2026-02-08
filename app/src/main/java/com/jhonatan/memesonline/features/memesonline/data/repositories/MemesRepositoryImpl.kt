package com.jhonatan.memesonline.features.memesonline.data.repositories

import com.jhonatan.memesonline.core.network.MemeApi
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.mapper.toDomain
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeUpdateRequest
import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeUploadRequest
import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository

class MemesRepositoryImpl(
    private val api: MemeApi
) : MemesRepository {

    override suspend fun getAllMemes(): List<Meme> {
        val response = api.getMemes()
        return response.map { it.toDomain() }
    }

    override suspend fun uploadMeme(title: String, base64Image: String): Result<Unit> {
        return try {
            val response = api.uploadMeme(MemeUploadRequest(title, base64Image))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorMsg = when(response.code()) {
                    401 -> "Sesión expirada. Vuelve a iniciar sesión."
                    413 -> "La imagen es demasiado pesada."
                    else -> "Error al subir el meme (${response.code()})"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con el servidor"))
        }
    }

    override suspend fun updateMeme(id: String, title: String?, image: String?): Result<Unit> {
        return try {
            val response = api.updateMeme(id, MemeUpdateRequest(title, image))
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error al actualizar: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMeme(id: String): Result<Unit> {
        return try {
            val response = api.deleteMeme(id)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error al eliminar: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}