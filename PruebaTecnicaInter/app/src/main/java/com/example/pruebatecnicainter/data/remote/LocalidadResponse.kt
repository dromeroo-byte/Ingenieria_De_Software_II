package com.example.pruebatecnicainter.data.remote

/**
 * Representa cada localidad devuelta por el endpoint ObtenerLocalidadesRecogidas.
 * Solo declaramos los campos que pide la prueba; Gson ignora el resto.
 */
data class LocalidadResponse(
    val AbreviacionCiudad: String? = null,
    val NombreCompleto: String? = null
)