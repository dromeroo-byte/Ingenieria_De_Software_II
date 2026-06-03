package com.example.pruebatecnicainter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa la tabla 'tabla_esquema' en la base de datos local.
 * Almacena cada una de las tablas devueltas por el endpoint ObtenerEsquema.
 */
@Entity(tableName = "tabla_esquema")
data class TablaEsquemaEntity(
    // Clave primaria autogenerada por Room para cada fila.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombreTabla: String,
    val pk: String,
    val numeroCampos: Int,
    val fechaActualizacion: String
)