package com.jhonatan.memesonline.features.memesonline.presentation.screens

import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme

data class MemesUiState(
    val isLoading: Boolean = false,
    val memes: List<Meme> = emptyList(),
    val error: String? = null,
    val isUploading: Boolean = false
)