package com.example.edufinance.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.edufinance.ui.theme.EduFinanceTheme
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

class RegisterEmailUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ✅ Caso 1: Correo válido
    @Test
    fun ingresarCorreoValido_registroExitoso() {
        var registroExitoso = false

        composeTestRule.setContent {
            EduFinanceTheme {
                RegisterScreen(
                    onRegisterSuccess = { registroExitoso = true },
                    onNavigateToLogin = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Marco Pérez")
        composeTestRule.onNodeWithText("Correo electrónico").performTextInput("marco@test.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Abcdef@123")

        composeTestRule.onNodeWithText("Registrarse").performClick()

        assertTrue("El registro debería completarse correctamente con un correo válido.", registroExitoso)
    }

    // ❌ Caso 2: Correo inválido (sin arroba)
    @Test
    fun ingresarCorreoInvalido_muestraError() {
        var registroExitoso = false

        composeTestRule.setContent {
            EduFinanceTheme {
                RegisterScreen(
                    onRegisterSuccess = { registroExitoso = true },
                    onNavigateToLogin = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Marco Pérez")
        composeTestRule.onNodeWithText("Correo electrónico").performTextInput("marcotest.com") // sin '@'
        composeTestRule.onNodeWithText("Contraseña").performTextInput("Abcdef@123")

        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Debe aparecer el mensaje de error
        composeTestRule.onNodeWithText("Correo inválido")
            .assertExists("No se mostró el mensaje de error esperado para correo inválido.")

        assertFalse("El registro no debería completarse con un correo inválido.", registroExitoso)
    }
}

