package com.example.garajeapp

// Objeto singleton que mantiene el listado de coches guardados en la memoria de la app
object Garaje {
    val coches = mutableListOf<Coche>()
}
