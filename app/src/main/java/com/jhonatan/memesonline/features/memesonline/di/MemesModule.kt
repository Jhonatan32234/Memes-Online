package com.jhonatan.memesonline.features.memesonline.di

import com.jhonatan.memesonline.core.di.AppContainer
import com.jhonatan.memesonline.features.memesonline.data.repositories.MemesRepositoryImpl
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository
import com.jhonatan.memesonline.features.memesonline.domain.usecases.DeleteMemeUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.GetMemesUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UpdateMemeUseCase
import com.jhonatan.memesonline.features.memesonline.domain.usecases.UploadMemeUseCase
import com.jhonatan.memesonline.features.memesonline.presentation.viewmodel.MemesViewModelFactory

class MemesModule(
    private val appContainer: AppContainer
) {
    private val repository: MemesRepository by lazy {
        MemesRepositoryImpl(appContainer.memeApi)
    }

    private fun provideGetMemesUseCase(): GetMemesUseCase {
        return GetMemesUseCase(repository)
    }

    private fun provideUploadMemeUseCase(): UploadMemeUseCase {
        return UploadMemeUseCase(repository)
    }

    private fun provideUpdateMemeUseCase(): UpdateMemeUseCase {
        return UpdateMemeUseCase(repository)
    }

    private fun provideDeleteMemeUseCase(): DeleteMemeUseCase {
        return DeleteMemeUseCase(repository)
    }

    fun provideMemesViewModelFactory(): MemesViewModelFactory {
        return MemesViewModelFactory(
            getMemesUseCase = provideGetMemesUseCase(),
            uploadMemeUseCase = provideUploadMemeUseCase(),
            updateMemeUseCase = provideUpdateMemeUseCase(),
            deleteMemeUseCase = provideDeleteMemeUseCase()
        )
    }
}
