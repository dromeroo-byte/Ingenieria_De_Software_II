package com.example.pruebatecnicainter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO para la tabla 'tabla_esquema'.
 * Define las operaciones de acceso a las tablas del esquema.
 */
@Dao
interface TablaEsquemaDao {

    /**
     * Inserta una lista completa de tablas del esquema.
     * Si hay conflicto, reemplaza los registros existentes.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarTablas(tablas: List<TablaEsquemaEntity>)

    /**
     * Obtiene todas las tablas del esquema almacenadas,
     * ordenadas alfabéticamente por nombre.
     */
    @Query("SELECT * FROM tabla_esquema ORDER BY nombreTabla ASC")
    suspend fun obtenerTablas(): List<TablaEsquemaEntity>

    /**
     * Borra todas las tablas almacenadas.
     * Útil para refrescar el esquema antes de volver a guardarlo.
     */
    @Query("DELETE FROM tabla_esquema")
    suspend fun limpiarTablas()
}