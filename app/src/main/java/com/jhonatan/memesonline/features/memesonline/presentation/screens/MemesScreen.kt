package com.jhonatan.memesonline.features.memesonline.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhonatan.memesonline.core.utils.ImageUtils
import com.jhonatan.memesonline.features.memesonline.presentation.components.MemeCard
import com.jhonatan.memesonline.features.memesonline.presentation.viewmodel.MemesViewModel
import com.jhonatan.memesonline.features.memesonline.presentation.viewmodel.MemesViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemesScreen(viewModel: MemesViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val base64 = ImageUtils.uriToBase64(context, it)
            if (base64 != null) {
                viewModel.onImageSelected(base64)
            }
        }
    }

    if (uiState.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onShowDialog(false) },
            title = {
                Text(if (uiState.editingMemeId == null) "Subir nuevo meme" else "Editar meme")
            },
            text = {
                Column (verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = uiState.memeTitle,
                        onValueChange = { viewModel.onTitleChange(it) },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.selectedBase64.isNotEmpty())
                                Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(8.dp))
                        Text(
                            if (uiState.selectedBase64.isNotEmpty()) "Imagen seleccionada ✓"
                            else "Seleccionar Imagen"
                        )
                    }

                    if (uiState.editingMemeId != null && uiState.selectedBase64.isEmpty()) {
                        Text(
                            "Nota: Si no seleccionas una imagen, se mantendrá la actual.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.saveMeme() },
                    // Validamos que si es nuevo, obligatoriamente tenga imagen
                    enabled = uiState.editingMemeId != null || uiState.selectedBase64.isNotEmpty()
                ) {
                    Text(if (uiState.editingMemeId == null) "Publicar" else "Guardar Cambios")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onShowDialog(false) }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { launcher.launch("image/*") }) {
                if (uiState.isUploading) CircularProgressIndicator()
                else Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(uiState.memes) { meme ->
                            MemeCard(
                                meme = meme,
                                onEdit = { viewModel.onEditClick(it) },
                                onDelete = { viewModel.deleteMeme(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}