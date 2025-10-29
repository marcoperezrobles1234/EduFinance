package com.example.edufinance

import com.example.edufinance.ui.utils.isValidPassword
import org.junit.Assert.*
import org.junit.Test

class PasswordTest {

    @Test
    fun `password valida devuelve true`() {
        assertTrue(isValidPassword("Hola123!"))
    }

    @Test
    fun `password sin mayuscula devuelve false`() {
        assertFalse(isValidPassword("hola123!"))
    }
}

