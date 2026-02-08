package com.jhonatan.memesonline.features.auth.data.datasources.remote.mapper

import com.jhonatan.memesonline.features.auth.data.datasources.remote.model.LoginResponse
import com.jhonatan.memesonline.features.auth.domain.entities.User

fun LoginResponse.toDomain(): User {
    return User(
        id = this.userId ?: "",
        name = this.userName ?: "Usuario Desconocido",
        token = this.token ?: ""
    )
}