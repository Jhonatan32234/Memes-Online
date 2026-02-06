package com.jhonatan.memesonline.features.auth.presentation.screens

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)