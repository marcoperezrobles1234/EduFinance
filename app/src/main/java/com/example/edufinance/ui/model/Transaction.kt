package com.example.edufinance.ui.model

// Clase de datos que representa una transacción financiera
data class Transaction(
    val id: Int,              // Identificador único de la transacción
    val monto: Double,        // Cantidad de dinero involucrada
    val tipo: String,         // Tipo de transacción: puede ser "Ingreso" o "Gasto"
    val fecha: String         // Fecha en la que se realizó la transacción
)



