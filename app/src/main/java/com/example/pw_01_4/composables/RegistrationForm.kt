package com.example.pw_01_4.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
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
fun RegistrationForm(
    isLoading: Boolean,
    nameMaxLength: Int,
    emailMaxLength: Int,
    passwordMaxLength: Int,
    onRegister: (String, String, String, String) -> Unit,
    onSwitchToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AuthHeader(title = "Реєстрація")

        AuthTextField(
            value = name,
            onValueChange = { name = it },
            label = "Ім'я",
            maxLength = nameMaxLength
        )

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

        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Підтвердження пароля",
            maxLength = passwordMaxLength,
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Text(
            text = "Пароль: 8-30 символів, літера, цифра та спецсимвол",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        SubmitButton(
            text = "Зареєструватися",
            isLoading = isLoading,
            onClick = { onRegister(name, email, password, confirmPassword) }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Вже є акаунт?")
            TextButton(onClick = onSwitchToLogin, enabled = !isLoading) {
                Text("Увійти")
            }
        }
    }
}