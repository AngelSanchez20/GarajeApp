package com.example.garajeapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MarcaInfoDao {

    @Query("SELECT * FROM marca_info WHERE LOWER(marca) = LOWER(:marca) LIMIT 1")
    suspend fun obtenerPorMarca(marca: String): MarcaInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(marcaInfo: MarcaInfo)
}
