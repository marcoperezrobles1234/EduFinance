package com.example.edufinance.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.concurrent.atomic.AtomicLong

data class ReminderUi(
    val id: Long,
    val text: String,
    val createdAt: Long = System.currentTimeMillis()
)

class RemindersViewModel : ViewModel() {

    private val genId = AtomicLong(1L)
    val reminders = mutableStateListOf<ReminderUi>()

    var mensaje: String = ""
        private set

    fun addReminder(text: String) {
        val t = text.trim()
        if (t.isEmpty()) return
        reminders.add(0, ReminderUi(id = genId.getAndIncrement(), text = t))
        mensaje = "Recordatorio agregado"
    }

    fun updateReminder(id: Long, newText: String) {
        val t = newText.trim()
        if (t.isEmpty()) return
        val idx = reminders.indexOfFirst { it.id == id }
        if (idx != -1) {
            reminders[idx] = reminders[idx].copy(text = t)
            mensaje = "Recordatorio actualizado"
        }
    }

    fun deleteReminder(id: Long) {
        reminders.removeAll { it.id == id }
        mensaje = "Recordatorio eliminado"
    }
}
//Actualizado (3)

