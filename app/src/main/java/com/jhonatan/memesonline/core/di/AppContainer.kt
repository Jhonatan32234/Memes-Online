package com.jhonatan.memesonline.core.di

import android.content.Context
import com.jhonatan.memesonline.core.network.MemeApi
import com.jhonatan.memesonline.features.auth.data.repositories.AuthRepositoryImpl
import com.jhonatan.memesonline.features.auth.domain.repositories.AuthRepository
import com.jhonatan.memesonline.core.network.AuthApi
import com.jhonatan.memesonline.features.memesonline.domain.repositories.MemesRepository
import com.jhonatan.memesonline.features.memesonline.data.repositories.MemesRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        AuthRepositoryImpl.accessToken?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        chain.proceed(request.build())
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://apimemes.jhonatanzc.fun/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val memeApi: MemeApi by lazy {
        retrofit.create(MemeApi::class.java)
    }
    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val memesRepository: MemesRepository by lazy {
        MemesRepositoryImpl(memeApi)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApi)
    }

}