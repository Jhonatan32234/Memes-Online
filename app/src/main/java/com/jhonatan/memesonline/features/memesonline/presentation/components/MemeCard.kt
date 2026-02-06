package com.jhonatan.memesonline.features.memesonline.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jhonatan.memesonline.core.utils.ImageUtils
import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme

@Composable
fun MemeCard(meme: Meme, modifier: Modifier = Modifier) {
    val bitmap = remember (meme.imageUrl) {
        ImageUtils.decodeBase64ToBitmap(meme.imageUrl)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = meme.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Publicado por: ${meme.authorName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AsyncImage(
                model = bitmap,
                contentDescription = "Meme: ${meme.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 400.dp)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = meme.uploadDate,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}