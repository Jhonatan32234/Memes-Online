package com.jhonatan.memesonline.features.auth.di

import com.jhonatan.memesonline.core.di.AppContainer
import com.jhonatan.memesonline.features.auth.domain.usecases.LoginUseCase
import com.jhonatan.memesonline.features.auth.domain.usecases.RegisterUseCase
import com.jhonatan.memesonline.features.auth.presentation.screens.AuthViewModelFactory

class AuthModule(
    private val appContainer: AppContainer
) {
    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory(
            LoginUseCase(appContainer.authRepository),
            RegisterUseCase(appContainer.authRepository)
        )
    }
}