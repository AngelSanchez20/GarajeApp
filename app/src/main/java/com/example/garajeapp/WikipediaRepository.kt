package com.example.garajeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WikipediaRepository(
    private val marcaInfoDao: MarcaInfoDao,
    private val apiService: WikipediaApiService = RetrofitClient.apiService
) {

    suspend fun obtenerInfoMarca(marca: String): WikipediaResult = withContext(Dispatchers.IO) {
        if (marca.isBlank()) {
            return@withContext WikipediaResult(null, false, "La marca no es válida")
        }

        val marcaLimpia = marca.trim().replaceFirstChar { it.uppercase() }

        try {
            val response = apiService.obtenerResumenMarca(marcaLimpia)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val infoGuardar = MarcaInfo(
                        marca = marca.trim(),
                        titulo = body.title ?: marcaLimpia,
                        descripcion = body.extract ?: "Sin información disponible.",
                        imagenUrl = body.thumbnail?.source
                    )
                    marcaInfoDao.insertar(infoGuardar)
                    return@withContext WikipediaResult(infoGuardar, false)
                } else {
                    android.util.Log.e("WIKI_DEBUG", "El body de la respuesta es NULL")
                }
            } else {
                android.util.Log.e(
                    "WIKI_DEBUG",
                    "Error HTTP: ${response.code()} - ${response.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            // AQUÍ VEMOS EL ERROR EXACTO EN EL LOGCAT
            android.util.Log.e("WIKI_DEBUG", "Excepción capturada: ${e.message}", e)
        }

        // Modo Offline: Caché local Room
        val cacheLocal = marcaInfoDao.obtenerPorMarca(marca.trim())
        if (cacheLocal != null) {
            return@withContext WikipediaResult(cacheLocal, true)
        } else {
            return@withContext WikipediaResult(
                null,
                true,
                "Sin conexión ni caché para \"$marca\"\n(Introduzca correctamente el nombre de la MARCA)"
            )
        }
    }
}