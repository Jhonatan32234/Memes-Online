package com.jhonatan.memesonline.features.auth.domain.entities

data class User(
    val id: String,
    val name: String,
    val token: String
)