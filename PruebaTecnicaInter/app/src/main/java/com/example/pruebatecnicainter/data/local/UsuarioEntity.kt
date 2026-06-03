package com.example.pruebatecnicainter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa la tabla 'usuario' en la base de datos local SQLite.
 * Almacena los datos obtenidos tras un login exitoso.
 */
@Entity(tableName = "usuario")
data class UsuarioEntity(
    // Clave primaria autogenerada. Usamos un id fijo (1) porque
    // solo guardamos el usuario actualmente autenticado.
    @PrimaryKey
    val id: Int = 1,

    val usuario: String,
    val identificacion: String,
    val nombre: String
)