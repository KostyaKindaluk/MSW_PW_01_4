package com.example.pw_01_4.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.pw_01_4.data.AuthManager

@Composable
fun AuthScreen() {
    val authManager = remember { AuthManager() }

    val scope = rememberCoroutineScope()

    var isRegisterMode by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isRegisterMode) {
                RegistrationForm(
                    isLoading = isLoading,
                    nameMaxLength = authManager.nameMaxLength,
                    emailMaxLength = authManager.emailMaxLength,
                    passwordMaxLength = authManager.passwordMaxLength,
                    onRegister = { name, email, password, confirmPassword ->
                        scope.launch {
                            isLoading = true
                            message = ""
                            val result = authManager.register(name, email, password, confirmPassword)
                            isLoading = false
                            message = result.message
                            isSuccess = result.isSuccess
                            if (result.isSuccess) isRegisterMode = false
                        }
                    },
                    onSwitchToLogin = {
                        isRegisterMode = false
                        message = ""
                    }
                )
            } else {
                LoginForm(
                    isLoading = isLoading,
                    emailMaxLength = authManager.emailMaxLength,
                    passwordMaxLength = authManager.passwordMaxLength,
                    onLogin = { email, password ->
                        scope.launch {
                            isLoading = true
                            message = ""
                            val result = authManager.login(email, password)
                            isLoading = false
                            message = result.message
                            isSuccess = result.isSuccess
                        }
                    },
                    onSwitchToRegister = {
                        isRegisterMode = true
                        message = ""
                    }
                )
            }

            if (isLoading) {
                LoadingIndicator()
            }

            if (message.isNotEmpty()) {
                ResultMessage(message = message, isSuccess = isSuccess)
            }
        }
    }
}