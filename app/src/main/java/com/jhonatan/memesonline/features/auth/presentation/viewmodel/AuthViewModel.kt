package com.jhonatan.memesonline.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhonatan.memesonline.features.auth.domain.usecases.LoginUseCase
import com.jhonatan.memesonline.features.auth.domain.usecases.RegisterUseCase
import com.jhonatan.memesonline.features.auth.presentation.screens.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    // Funciones para actualizar el estado desde la UI
    fun onEmailChanged(newValue: String) = _uiState.update { it.copy(email = newValue) }
    fun onPasswordChanged(newValue: String) = _uiState.update { it.copy(password = newValue) }
    fun onConfirmPasswordChanged(newValue: String) = _uiState.update { it.copy(confirmPassword = newValue) }

    fun onLogin(onSuccess: () -> Unit) {
        val email = _uiState.value.email
        val pass = _uiState.value.password

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            loginUseCase(email, pass).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun onRegister(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(error = "Las contraseñas no coinciden") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            registerUseCase(state.email, state.password).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }
}