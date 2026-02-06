package com.jhonatan.memesonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.jhonatan.memesonline.core.di.AppContainer
import com.jhonatan.memesonline.features.auth.di.AuthModule
import com.jhonatan.memesonline.features.auth.presentation.screens.LoginScreen
import com.jhonatan.memesonline.features.memesonline.di.MemesModule
import com.jhonatan.memesonline.ui.theme.MemesOnlineTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.jhonatan.memesonline.features.auth.presentation.screens.RegisterScreen
import com.jhonatan.memesonline.features.memesonline.presentation.screens.MemesScreen

class MainActivity : ComponentActivity() {
    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)

        val authModule = AuthModule(appContainer)
        val memesModule = MemesModule(appContainer)

        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface (color = MaterialTheme.colorScheme.background) {
                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> {
                            LoginScreen(
                                factory = authModule.provideAuthViewModelFactory(),
                                onLoginSuccess = { currentScreen = "memes" },
                                onNavigateToRegister = { currentScreen = "register" }
                            )
                        }
                        "register" -> {
                            RegisterScreen(
                                factory = authModule.provideAuthViewModelFactory(),
                                onRegisterSuccess = { currentScreen = "login" },
                                onNavigateToLogin = { currentScreen = "login" }
                            )
                        }
                        "memes" -> {
                            MemesScreen(
                                factory = memesModule.provideMemesViewModelFactory()
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemesOnlineTheme {
    }
}