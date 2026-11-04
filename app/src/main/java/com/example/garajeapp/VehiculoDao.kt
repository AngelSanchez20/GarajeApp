package com.example.garajeapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VehiculoDao {

    @Insert
    suspend fun insertar(vehiculo: Vehiculo)

    @Query("SELECT * FROM vehiculos ORDER BY id DESC")
    suspend fun obtenerTodas(): List<Vehiculo>

    @Delete
    suspend fun borrar(vehiculo: Vehiculo)
}