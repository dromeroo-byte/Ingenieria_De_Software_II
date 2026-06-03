package com.example.pruebatecnicainter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO (Data Access Object) para la tabla 'usuario'.
 * Define las operaciones de acceso a los datos del usuario.
 */
@Dao
interface UsuarioDao {

    /**
     * Inserta o reemplaza el usuario autenticado.
     * Si ya existe un registro con el mismo id, lo sobrescribe.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarUsuario(usuario: UsuarioEntity)

    /**
     * Obtiene el usuario almacenado (el actualmente autenticado).
     * @return el usuario, o null si no hay ninguno guardado.
     */
    @Query("SELECT * FROM usuario WHERE id = 1")
    suspend fun obtenerUsuario(): UsuarioEntity?
}