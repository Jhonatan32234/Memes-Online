package com.jhonatan.memesonline.features.memesonline.di

import com.jhonatan.memesonline.core.di.AppContainer
import com.jhonatan.memesonline.features.memesonline.data.repositories.MemesRepositoryImpl
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository
import com.jhonatan.memesonline.features.memesonline.domain.usecases.GetMemesUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UploadMemeUseCase
import com.jhonatan.memesonline.features.memesonline.presentation.viewmodel.MemesViewModelFactory

class MemesModule(private val appContainer: AppContainer) {

    private val repository: MemesRepository by lazy {
        MemesRepositoryImpl(appContainer.memeApi)
    }

    fun provideMemesViewModelFactory(): MemesViewModelFactory {
        return MemesViewModelFactory(
            GetMemesUseCase(repository),
            UploadMemeUseCase(repository)
        )
    }
}