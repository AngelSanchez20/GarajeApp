package com.example.garajeapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marca_info")
data class MarcaInfo(
    @PrimaryKey
    val marca: String,
    val titulo: String?,
    val descripcion: String?,
    val imagenUrl: String?
)
