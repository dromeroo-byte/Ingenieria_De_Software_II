package com.example.pruebatecnicainter.data.remote

/**
 * Representa cada tabla devuelta por el endpoint ObtenerEsquema.
 * Solo declaramos los campos que nos interesan; Gson ignora el resto.
 */
data class TablaEsquemaResponse(
    val NombreTabla: String? = null,
    val Pk: String? = null,
    val NumeroCampos: Int? = null,
    val BatchSize: Int? = null,
    val FechaActualizacionSincro: String? = null
)