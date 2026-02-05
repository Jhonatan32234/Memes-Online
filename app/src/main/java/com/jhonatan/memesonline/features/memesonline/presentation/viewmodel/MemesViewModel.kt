package com.jhonatan.memesonline.features.memesonline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonatan.memesonline.features.memesonline.domain.usecases.GetMemesUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UploadMemeUseCase
import com.jhonatan.memesonline.features.memesonline.presentation.screens.MemesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemesViewModel(
    private val getMemesUseCase: GetMemesUseCase,
    private val uploadMemeUseCase: UploadMemeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MemesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMemes()
    }

    fun loadMemes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getMemesUseCase().fold(
                onSuccess = { list -> _uiState.update { it.copy(isLoading = false, memes = list) } },
                onFailure = { _uiState.update { it.copy(isLoading = false, error = "Error al cargar memes") } }
            )
        }
    }

    fun uploadMeme(title: String, uri: String) {
        _uiState.update { it.copy(isUploading = true) }
        viewModelScope.launch {
            uploadMemeUseCase(title, uri).onSuccess {
                loadMemes()
            }
            _uiState.update { it.copy(isUploading = false) }
        }
    }
}