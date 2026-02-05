package com.jhonatan.memesonline.features.memesonline.data.datasources.remote.mapper

import com.jhonatan.memesonline.features.memesonline.data.datasources.remote.model.MemeDto
import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme

fun MemeDto.toDomain(): Meme {
    return Meme(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        authorName = this.author,
        uploadDate = this.date
    )
}