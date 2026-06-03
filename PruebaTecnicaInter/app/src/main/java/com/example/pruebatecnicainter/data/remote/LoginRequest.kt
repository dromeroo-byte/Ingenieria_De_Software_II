package com.example.pruebatecnicainter.data.remote

/**
 * Cuerpo (body) de la petición de login.
 * Los valores de Usuario y Password vienen codificados en Base64,
 * tal como los especifica la prueba técnica.
 */
data class LoginRequest(
    val Mac: String = "",
    val NomAplicacion: String = "Controller APP",
    val Password: String = "SW50ZXIyMDIx\n",
    val Path: String = "",
    val Usuario: String = "cGFtLm1lcmVkeTIx\n"
)