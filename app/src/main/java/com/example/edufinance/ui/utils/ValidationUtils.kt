package com.example.edufinance.ui.utils

// Función que valida si una contraseña cumple con los requisitos mínimos
fun isValidPassword(password: String): Boolean {
    // Expresión regular:
    // ^                 -> inicio de la cadena
    // (?=.*[a-z])       -> al menos una letra minúscula
    // (?=.*[A-Z])       -> al menos una letra mayúscula
    // (?=.*[@#\$%^&+=!?.]) -> al menos un carácter especial
    // .{8,}             -> mínimo 8 caracteres en total
    // $                 -> fin de la cadena
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!?.]).{8,}\$")
    return passwordRegex.matches(password) // Devuelve true si la contraseña cumple el patrón
}

// Función que valida si un correo electrónico tiene formato válido
fun isValidEmail(email: String): Boolean {
    // Usa el patrón de Android para emails y verifica si coincide con el string
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}





