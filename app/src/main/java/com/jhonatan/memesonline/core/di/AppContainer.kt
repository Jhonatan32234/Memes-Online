package com.jhonatan.memesonline.core.di

import android.content.Context
import com.jhonatan.memesonline.core.network.MemeApi
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository
import com.jhonatan.memesonline.features.memesonline.data.repositories.MemesRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api-futura/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val memeApi: MemeApi by lazy {
        retrofit.create(MemeApi::class.java)
    }

    val memesRepository: MemesRepository by lazy {
        MemesRepositoryImpl(memeApi)
    }

}