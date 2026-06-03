package com.example.pruebatecnicainter.data.repository

import com.example.pruebatecnicainter.data.remote.LocalidadResponse
import com.example.pruebatecnicainter.data.remote.RetrofitClient

/**
 * Repositorio de Localidades.
 * Responsabilidad única: obtener las localidades de recogida desde la API.
 * No persiste datos localmente (el PDF solo pide consumir y exhibir).
 */
class LocalidadesRepository {

    /**
     * Consume el endpoint de localidades.
     * @return la lista de localidades, o null si hubo error.
     */
    suspend fun obtenerLocalidades(): List<LocalidadResponse>? {
        return try {
            val response = RetrofitClient.apiService.obtenerLocalidades()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}