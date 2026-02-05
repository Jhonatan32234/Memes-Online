package com.jhonatan.memesonline.features.memesonline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhonatan.memesonline.features.memesonline.domain.usecases.GetMemesUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UploadMemeUseCase


class MemesViewModelFactory(
    private val getMemesUseCase: GetMemesUseCase,
    private val uploadMemeUseCase: UploadMemeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemesViewModel(getMemesUseCase, uploadMemeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}