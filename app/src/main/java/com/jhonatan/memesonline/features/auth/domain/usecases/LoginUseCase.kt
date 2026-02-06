package com.jhonatan.memesonline.features.auth.domain.usecases

import com.jhonatan.memesonline.features.auth.domain.repositories.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String) = repository.login(email, pass)
}