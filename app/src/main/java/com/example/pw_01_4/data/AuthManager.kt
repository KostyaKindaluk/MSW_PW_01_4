package com.example.pw_01_4.data

import org.apache.commons.validator.routines.EmailValidator
import org.apache.commons.validator.routines.RegexValidator
import kotlinx.coroutines.delay

data class AuthResult(
    val isSuccess: Boolean,
    val message: String
)

class AuthManager {
    val nameMaxLength = 50
    val emailMaxLength = 100
    val passwordMaxLength = 30
    private val nameMinLength = 2
    private val passwordMinLength = 8

    private val emailValidator = EmailValidator.getInstance()

    private val nameValidator = RegexValidator("^[\\p{L} '’ʼ-]+$")

    private val passwordValidator = RegexValidator("^(?=.*\\p{L})(?=.*\\d)(?=.*[^\\p{L}\\d\\s])\\S+$")

    private val users = mutableMapOf<String, Pair<String, String>>()

    fun validateName(name: String): String? {
        return when {
            name.trim().length < nameMinLength -> "ім'я має містити щонайменше $nameMinLength символи"
            !nameValidator.isValid(name.trim()) -> "ім'я може містити лише літери, пробіли, дефіси та апострофи"
            else -> null
        }
    }

    fun validateEmail(email: String): String? {
        return if (!emailValidator.isValid(email.trim())) "некоректна адреса пошти" else null
    }

    fun validatePassword(password: String): String? {
        return when {
            password.length < passwordMinLength -> "пароль має містити щонайменше $passwordMinLength символів"
            !passwordValidator.isValid(password) -> "пароль має містити літеру, цифру, спецсимвол і не містити пробілів"
            else -> null
        }
    }

    suspend fun register(name: String, email: String, password: String, confirmPassword: String): AuthResult {
        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return AuthResult(false, "Введіть усі потрібні дані")
        }

        val error = validateName(name)
            ?: validateEmail(email)
            ?: validatePassword(password)
            ?: if (password != confirmPassword) "паролі не співпадають" else null

        if (error != null) {
            return AuthResult(false, "Помилка: $error")
        }

        delay(1500)

        val key = email.trim().lowercase()
        if (users.containsKey(key)) {
            return AuthResult(false, "Помилка: користувач з такою поштою вже існує")
        }

        users[key] = Pair(name.trim(), password)
        return AuthResult(true, "Реєстрація успішна! Тепер увійдіть у систему")
    }

    suspend fun login(email: String, password: String): AuthResult {
        if (email.isBlank() || password.isBlank()) {
            return AuthResult(false, "Введіть усі потрібні дані")
        }

        val emailError = validateEmail(email)
        if (emailError != null) {
            return AuthResult(false, "Помилка: $emailError")
        }

        delay(1500)

        val user = users[email.trim().lowercase()]
        if (user == null || user.second != password) {
            return AuthResult(false, "Помилка: невірна пошта або пароль")
        }

        return AuthResult(true, "Авторизація успішна! Вітаємо, ${user.first}")
    }
}