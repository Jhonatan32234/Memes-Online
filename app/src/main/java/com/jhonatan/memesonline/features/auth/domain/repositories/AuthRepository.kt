package com.jhonatan.memesonline.features.auth.domain.repositories

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<Unit>
    suspend fun register(email: String, pass: String): Result<Unit>
}