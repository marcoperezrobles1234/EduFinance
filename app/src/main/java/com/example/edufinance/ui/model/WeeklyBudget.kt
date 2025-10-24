package com.example.edufinance.ui.model

// Clase de datos que representa el presupuesto semanal dividido por categorías
data class WeeklyBudget(
    var alimentos: Double = 0.0,    // Gasto destinado a alimentos
    var transporte: Double = 0.0,   // Gasto en transporte (autobús, gasolina, etc.)
    var libros: Double = 0.0,       // Gasto en libros o material educativo
    var ocio: Double = 0.0,         // Gasto en entretenimiento u ocio
    var ahorro: Double = 0.0        // Cantidad destinada al ahorro
) {
    // Función que calcula el total del presupuesto semanal sumando todas las categorías
    fun total(): Double {
        return alimentos + transporte + libros + ocio + ahorro
    }
}



