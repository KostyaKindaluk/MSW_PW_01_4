package com.example.pw_01_4.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    isLoading: Boolean,
    emailMaxLength: Int,
    passwordMaxLength: Int,
    onLogin: (String, String) -> Unit,
    onSwitchToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AuthHeader(title = "Авторизація")

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Пошта",
            maxLength = emailMaxLength,
            keyboardType = KeyboardType.Email
        )

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Пароль",
            maxLength = passwordMaxLength,
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        SubmitButton(text = "Увійти", isLoading = isLoading, onClick = { onLogin(email, password) })

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Ще немає акаунту?")
            TextButton(onClick = onSwitchToRegister, enabled = !isLoading) {
                Text("Зареєструватися")
            }
        }
    }
}