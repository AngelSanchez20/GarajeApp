package com.example.garajeapp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

class DetalleActivity : ComponentActivity() {

    // Lo declaramos aquí arriba para poder liberarlo luego en onDestroy
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val imagen = findViewById<ImageView>(R.id.imagenDetalle)
        val textoMarcaModelo = findViewById<TextView>(R.id.textoMarcaModeloDetalle)
        val textoPotencia = findViewById<TextView>(R.id.textoPotenciaDetalle)
        val textoCategoria = findViewById<TextView>(R.id.textoCategoriaDetalle)
        val botonVolver = findViewById<Button>(R.id.botonVolverDetalle)

        // Recogemos los datos que nos manda la lista
        val marca = intent.getStringExtra("MARCA") ?: ""
        val modelo = intent.getStringExtra("MODELO") ?: ""
        val potencia = intent.getIntExtra("POTENCIA", 0)

        textoMarcaModelo.text = "$marca $modelo"
        textoPotencia.text = "Potencia: $potencia CV"

        // Valores por defecto: Superdeportivo
        var categoria = "Superdeportivo"
        var imagenCategoria = R.drawable.coche_super
        var sonido = R.raw.motor_super

        if (potencia < 100) {
            categoria = "Utilitario"
            imagenCategoria = R.drawable.coche_utilitario
            sonido = R.raw.motor_utilitario
        } else if (potencia <= 300) {
            categoria = "Deportivo"
            imagenCategoria = R.drawable.coche_deportivo
            sonido = R.raw.motor_deportivo
        }

        textoCategoria.text = "Categoría: $categoria"
        imagen.setImageResource(imagenCategoria)

        mediaPlayer = MediaPlayer.create(this, sonido)
        mediaPlayer?.start()

        botonVolver.setOnClickListener {
            finish()
        }
    }

    // Cuando se cierra la activity, liberamos el sonido
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}