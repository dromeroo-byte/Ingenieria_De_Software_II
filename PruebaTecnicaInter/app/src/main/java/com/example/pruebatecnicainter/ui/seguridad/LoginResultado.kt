package com.example.pruebatecnicainter.ui.seguridad

/**
 * Representa el resultado de un intento de login.
 * Puede ser Exito (con los datos del usuario) o Error (con un mensaje).
 * Al ser 'sealed', obliga a manejar todos los casos posibles.
 */
sealed class LoginResultado {

    /** Login exitoso: contiene los datos extraídos del usuario. */
    data class Exito(
        val usuario: String,
        val identificacion: String,
        val nombre: String
    ) : LoginResultado()

    /** Login fallido: contiene el mensaje de alerta a mostrar. */
    data class Error(val mensaje: String) : LoginResultado()
}