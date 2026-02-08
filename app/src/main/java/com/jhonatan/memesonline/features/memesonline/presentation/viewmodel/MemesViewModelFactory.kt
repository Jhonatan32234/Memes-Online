package com.jhonatan.memesonline.features.memesonline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhonatan.memesonline.features.memesonline.domain.usecases.DeleteMemeUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.GetMemesUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UpdateMemeUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UploadMemeUseCase


class MemesViewModelFactory(
    private val getMemesUseCase: GetMemesUseCase,
    private val uploadMemeUseCase: UploadMemeUseCase,
    private val updateMemeUseCase: UpdateMemeUseCase,
    private val deleteMemeUseCase: DeleteMemeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemesViewModel(getMemesUseCase, uploadMemeUseCase, updateMemeUseCase, deleteMemeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}