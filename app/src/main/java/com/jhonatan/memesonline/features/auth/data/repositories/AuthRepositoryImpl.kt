package com.jhonatan.memesonline.features.auth.data.repositories

import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginRequest
import com.jhonatan.memesonline.features.auth.domain.repositories.AuthRepository
import com.jhonatan.memesonline.features.auth.domain.usecases.AuthApi
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    companion object {
        var accessToken: String? = null
    }
    override suspend fun login(email: String, pass: String): Result<Unit> {
        return try {
            val response = api.login(LoginRequest(email, pass))
            accessToken = response.token
            Result.success(Unit)
        } catch (e: HttpException) {
            val message = when (e.code()) {
                401 -> "El correo o la contraseña son incorrectos."
                404 -> "El servidor de autenticación no está disponible."
                500 -> "Tenemos problemas internos, intenta más tarde."
                else -> "Ocurrió un error inesperado (Error: ${e.code()})"
            }
            Result.failure(Exception(message))
        } catch (e: Exception) {
            Result.failure(Exception("No hay conexión a internet."))
        }
    }

    override suspend fun register(email: String, pass: String): Result<Unit> {
        return try {
            val response = api.register(LoginRequest(email, pass))
            if (response.isSuccessful) Result.success(Unit)
            else {
                val message = when (response.code()) {
                    409 -> "Este correo ya está registrado."
                    400 -> "Los datos enviados no son válidos."
                    else -> "No se pudo crear la cuenta."
                }
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red. Verifica tu conexión."))
        }
    }

}