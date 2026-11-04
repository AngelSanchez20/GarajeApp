package com.example.garajeapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehiculos")
data class Vehiculo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val marca: String,
    val modelo: String,
    val potencia: Int
)