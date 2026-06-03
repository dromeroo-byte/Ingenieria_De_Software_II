package com.example.pruebatecnicainter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos local SQLite de la aplicación, gestionada con Room.
 * Reúne las entidades (tablas) y provee acceso a los DAOs.
 */
@Database(
    entities = [UsuarioEntity::class, TablaEsquemaEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /** Acceso a las operaciones de la tabla 'usuario'. */
    abstract fun usuarioDao(): UsuarioDao
    abstract fun tablaEsquemaDao(): TablaEsquemaDao

    companion object {
        // @Volatile asegura que el valor siempre esté actualizado entre hilos.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Devuelve la única instancia de la base de datos (patrón singleton).
         * Si no existe, la crea de forma segura.
         */
        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "prueba_tecnica.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}