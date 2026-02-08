package com.jhonatan.memesonline.features.memesonline.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jhonatan.memesonline.core.utils.ImageUtils
import com.jhonatan.memesonline.features.memesonline.domain.entities.Meme

@Composable
fun MemeCard(
    meme: Meme,
    onEdit: (Meme) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUserId = com.jhonatan.memesonline.features.auth.data.repositories.AuthRepositoryImpl.currentUserId
    val isOwner = currentUserId == meme.authorId
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = meme.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Publicado por: ${meme.authorName}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (isOwner) {
                    Row {
                        IconButton(onClick = { onEdit(meme) }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = Color.Blue
                            )
                        }
                        IconButton(onClick = { onDelete(meme.id) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Borrar",
                                tint = Color.Red
                            )
                        }
                    }
                }
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