package com.example.pruebatecnicainter.data.remote

/**
 * Representa la respuesta del endpoint de login.
 * Los campos son anulables (con '?') porque el ambiente de pruebas
 * puede devolver valores null, y la app debe manejarlos sin fallar.
 */
data class LoginResponse(
    val Usuario: String? = null,
    val Identificacion: String? = null,
    val Nombre: String? = null,
    val Apellido1: String? = null,
    val Apellido2: String? = null,
    val Cargo: String? = null,
    val MensajeResultado: Int? = null,
    val TokenJWT: String? = null
)