package com.jhonatan.memesonline.features.memesonline.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhonatan.memesonline.core.utils.ImageUtils
import com.jhonatan.memesonline.features.memesonline.presentation.components.MemeCard
import com.jhonatan.memesonline.features.memesonline.presentation.viewmodel.MemesViewModel
import com.jhonatan.memesonline.features.memesonline.presentation.viewmodel.MemesViewModelFactory


@Composable
fun MemesScreen(factory: MemesViewModelFactory) {
    val viewModel: MemesViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedBase64 by remember { mutableStateOf("") }
    var memeTitle by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val base64 = ImageUtils.uriToBase64(context, it)
            if (base64 != null) {
                selectedBase64 = base64
                showDialog = true // Abrimos el diálogo al elegir imagen
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Subir nuevo meme") },
            text = {
                OutlinedTextField(
                    value = memeTitle,
                    onValueChange = { memeTitle = it },
                    label = { Text("Título del meme") }
                )
            },
            confirmButton = {
                Button (onClick = {
                    viewModel.uploadMeme(memeTitle, selectedBase64)
                    showDialog = false
                    memeTitle = ""
                }) { Text("Subir") }
            },
            dismissButton = {
                TextButton (onClick = { showDialog = false }) { Text("Cancelar") }
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
    ) { padding ->
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn (modifier = Modifier.padding(padding)) {
                items(uiState.memes) { meme ->
                    MemeCard(meme = meme)
                }
            }
        }
    }
}
