package com.jhonatan.memesonline.features.auth.data.repositories

import android.util.Log
import com.jhonatan.memesonline.features.auth.data.datasources.remote.mapper.toDomain
import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginRequest
import com.jhonatan.memesonline.features.auth.domain.entities.User
import com.jhonatan.memesonline.features.auth.domain.repositories.AuthRepository
import com.jhonatan.memesonline.core.network.AuthApi
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    companion object {
        var accessToken: String? = null
        var currentUserId: String? = null // <--- Nueva variable para el ID
    }

    override suspend fun login(email: String, pass: String): Result<User> {
        return try {
            val response = api.login(LoginRequest(email, pass))

            val user = response.toDomain()

            accessToken = user.token
            currentUserId = user.id

            Result.success(user)
        } catch (e: HttpException) {
            val message = when (e.code()) {
                401 -> "El correo o la contraseña son incorrectos."
                500 -> "Problemas en el servidor, intenta más tarde."
                else -> "Error inesperado (${e.code()})"
            }
            Result.failure(Exception(message))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error detallado: ${e.message}", e)
            Result.failure(Exception("Sin conexión a internet."))
        }
    }

    override suspend fun register(email: String, pass: String): Result<Unit> {
        return try {
            val response = api.register(LoginRequest(email, pass))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val message = when (response.code()) {
                    409 -> "Este correo ya está registrado."
                    else -> "No se pudo crear la cuenta."
                }
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red detectado."))
        }
    }
}