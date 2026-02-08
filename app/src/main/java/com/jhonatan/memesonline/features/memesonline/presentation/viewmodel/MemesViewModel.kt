package com.jhonatan.memesonline.features.memesonline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme
import com.jhonatan.memesonline.features.memesonline.domain.usecases.DeleteMemeUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.GetMemesUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UpdateMemeUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UploadMemeUseCase
import com.jhonatan.memesonline.features.memesonline.presentation.screens.MemesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemesViewModel(
    private val getMemesUseCase: GetMemesUseCase,
    private val uploadMemeUseCase: UploadMemeUseCase,
    private val updateMemeUseCase: UpdateMemeUseCase,
    private val deleteMemeUseCase: DeleteMemeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMemes()
    }

    fun onEditClick(meme: Meme) {
        _uiState.update {
            it.copy(
                showDialog = true,
                memeTitle = meme.title,
                editingMemeId = meme.id,
                selectedBase64 = ""
            )
        }
    }

    fun onShowDialog(show: Boolean) {
        if (!show) {
            _uiState.update { it.copy(showDialog = false, editingMemeId = null, memeTitle = "", selectedBase64 = "") }
        } else {
            _uiState.update { it.copy(showDialog = true) }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(memeTitle = newTitle) }
    }

    fun onImageSelected(base64: String) {
        _uiState.update { it.copy(selectedBase64 = base64) }
        if (!_uiState.value.showDialog) {
            _uiState.update { it.copy(showDialog = true) }
        }
    }

    private fun loadMemes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getMemesUseCase()
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(isLoading = false, memes = list, error = null)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message ?: "Error desconocido")
                    }
                )
            }
        }
    }

    fun uploadMeme() {
        val title = uiState.value.memeTitle
        val base64 = uiState.value.selectedBase64

        _uiState.update { it.copy(isUploading = true, showDialog = false) }

        viewModelScope.launch {
            uploadMemeUseCase(title, base64).fold(
                onSuccess = {
                    _uiState.update { it.copy(isUploading = false, memeTitle = "") }
                    loadMemes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isUploading = false, error = error.message) }
                }
            )
        }
    }

    fun deleteMeme(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            deleteMemeUseCase(id).fold(
                onSuccess = { loadMemes() },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
            )
        }
    }

    fun saveMeme() {
        val state = uiState.value
        if (state.editingMemeId == null) {
            uploadMeme() // Lógica de creación que ya tenías
        } else {
            updateMeme(state.editingMemeId)
        }
    }

    private fun updateMeme(id: String) {
        val title = uiState.value.memeTitle
        val base64 = uiState.value.selectedBase64.takeIf { it.isNotEmpty() }

        _uiState.update { it.copy(isUploading = true, showDialog = false) }

        viewModelScope.launch {
            updateMemeUseCase(id, title, base64).fold(
                onSuccess = {
                    _uiState.update { it.copy(isUploading = false, editingMemeId = null, memeTitle = "") }
                    loadMemes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isUploading = false, error = error.message) }
                }
            )
        }
    }
}