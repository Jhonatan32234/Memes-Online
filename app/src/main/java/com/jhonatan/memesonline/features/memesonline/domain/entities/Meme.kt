package com.jhonatan.memesonline.features.memesonline.domain.entities

data class Meme(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authorName: String,
    val authorId: String,
    val uploadDate: String
)