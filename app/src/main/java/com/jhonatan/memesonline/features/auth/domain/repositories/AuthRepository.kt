package com.jhonatan.memesonline.features.auth.domain.repositories

import com.jhonatan.memesonline.features.auth.domain.entities.User

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<User>
    suspend fun register(email: String, pass: String): Result<Unit>
}