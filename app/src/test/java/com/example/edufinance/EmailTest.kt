package com.example.edufinance

import com.example.edufinance.ui.utils.isValidEmail
import org.junit.Assert.*
import org.junit.Test

class EmailTest {

    @Test
    fun `email valido devuelve true`() {
        assertTrue(isValidEmail("marco@example.com"))
    }

    @Test
    fun `email sin arroba devuelve false`() {
        assertFalse(isValidEmail("marcoexample.com"))
    }
}

