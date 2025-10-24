package com.example.edufinance.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edufinance.ui.model.WeeklyBudget

// ViewModel que maneja la lógica de la pantalla principal (Dashboard)
class DashboardViewModel : ViewModel() {

    // Saldo total disponible del usuario (estado reactivo)
    var saldoTotal by mutableStateOf(0.0)
        private set  // Solo puede modificarse dentro del ViewModel

    // Presupuesto semanal dividido por categorías
    var weeklyBudget by mutableStateOf(WeeklyBudget())
        private set

    // Mensajes que se muestran al usuario (errores o confirmaciones)
    var mensaje by mutableStateOf("")
        private set

    // Función para agregar dinero al saldo total
    fun agregarSaldo(monto: Double) {
        saldoTotal += monto
    }

    // Función para actualizar todo el presupuesto semanal de golpe
    fun actualizarBudget(
        alimentos: Double,
        transporte: Double,
        libros: Double,
        ocio: Double,
        ahorro: Double
    ) {
        val totalGasto = alimentos + transporte + libros + ocio + ahorro

        // Si el total del presupuesto supera el saldo disponible, muestra mensaje de error
        if (totalGasto > saldoTotal) {
            mensaje += "Saldo insuficiente, modifique los campos\n"
        } else {
            // Actualiza el presupuesto y descuenta del saldo total
            weeklyBudget = WeeklyBudget(alimentos, transporte, libros, ocio, ahorro)
            saldoTotal -= totalGasto
            mensaje += "Presupuesto actualizado\n"
        }
    }

    // Función para registrar un gasto en una categoría específica
    fun registrarGasto(categoria: String, monto: Double) {
        // Verifica si hay saldo suficiente para el gasto
        if (monto > saldoTotal) {
            mensaje += "Saldo insuficiente para $categoria. Por favor modifique el campo\n"
        } else {
            // Actualiza la categoría correspondiente sumando el monto
            weeklyBudget = when (categoria) {
                "Alimentos" -> weeklyBudget.copy(alimentos = weeklyBudget.alimentos + monto)
                "Transporte" -> weeklyBudget.copy(transporte = weeklyBudget.transporte + monto)
                "Libros" -> weeklyBudget.copy(libros = weeklyBudget.libros + monto)
                "Ocio" -> weeklyBudget.copy(ocio = weeklyBudget.ocio + monto)
                "Ahorro" -> weeklyBudget.copy(ahorro = weeklyBudget.ahorro + monto)
                else -> weeklyBudget
            }
            // Descarga el monto del saldo total y agrega mensaje de confirmación
            saldoTotal -= monto
            mensaje += "$categoria registrado: $${String.format("%.2f", monto)}\n"
        }
    }

    // Función para limpiar los mensajes del usuario
    fun limpiarMensaje() {
        mensaje = ""
    }
}










