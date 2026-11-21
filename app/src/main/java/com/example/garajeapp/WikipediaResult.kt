package com.example.garajeapp

data class WikipediaResult(
    val marcaInfo: MarcaInfo?,
    val esOffline: Boolean,
    val mensajeError: String? = null
)
