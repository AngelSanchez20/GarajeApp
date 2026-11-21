package com.example.garajeapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object Fotos {

    fun crearArchivo(context: Context): File {
        val directorio = File(context.filesDir, "fotos")
        if (!directorio.exists()) {
            directorio.mkdirs()
        }
        return File.createTempFile("foto_", ".jpg", directorio)
    }

    fun uriDe(context: Context, archivo: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            archivo
        )
    }

    fun copiarDesdeUri(context: Context, uri: Uri): String? {
        return try {
            val entrada = context.contentResolver.openInputStream(uri) ?: return null
            val archivoDestino = crearArchivo(context)
            val salida = FileOutputStream(archivoDestino)
            entrada.copyTo(salida)
            entrada.close()
            salida.close()
            archivoDestino.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun cargar(ruta: String?): Bitmap? {
        if (ruta.isNullOrEmpty()) return null
        val archivo = File(ruta)
        if (!archivo.exists()) return null
        return BitmapFactory.decodeFile(ruta)
    }
}
