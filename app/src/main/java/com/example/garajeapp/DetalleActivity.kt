package com.example.garajeapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetalleActivity : ComponentActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var viewModel: DetalleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val imagenFoto = findViewById<ImageView>(R.id.imagenFotoDetalle)
        val imagen = findViewById<ImageView>(R.id.imagenDetalle)
        val textoMarcaModelo = findViewById<TextView>(R.id.textoMarcaModeloDetalle)
        val textoPotencia = findViewById<TextView>(R.id.textoPotenciaDetalle)
        val textoCategoria = findViewById<TextView>(R.id.textoCategoriaDetalle)
        val textoUbicacion = findViewById<TextView>(R.id.textoUbicacionDetalle)
        val botonVolver = findViewById<Button>(R.id.botonVolverDetalle)

        // Elementos de la tarjeta de Wikipedia (API + Room Cache) sin logo
        val pbCargandoMarca = findViewById<ProgressBar>(R.id.pbCargandoMarca)
        val tvEstadoMarca = findViewById<TextView>(R.id.tvEstadoMarca)
        val tvTituloMarcaWiki = findViewById<TextView>(R.id.tvTituloMarcaWiki)
        val tvHistoriaMarcaWiki = findViewById<TextView>(R.id.tvHistoriaMarcaWiki)

        // Recogemos los datos que nos manda la lista
        val marca = intent.getStringExtra("MARCA") ?: ""
        val modelo = intent.getStringExtra("MODELO") ?: ""
        val potencia = intent.getIntExtra("POTENCIA", 0)
        val fotoPath = intent.getStringExtra("FOTO_PATH")

        textoMarcaModelo.text = "$marca $modelo"
        textoPotencia.text = "Potencia: $potencia CV"

        // Fotografía del vehículo si existe
        val bitmap = Fotos.cargar(fotoPath)
        if (bitmap != null) {
            imagenFoto.setImageBitmap(bitmap)
            imagenFoto.visibility = View.VISIBLE
        } else {
            imagenFoto.visibility = View.GONE
        }

        // Ubicación GPS si existe
        if (intent.hasExtra("LATITUD") && intent.hasExtra("LONGITUD")) {
            val lat = intent.getDoubleExtra("LATITUD", 0.0)
            val lon = intent.getDoubleExtra("LONGITUD", 0.0)
            textoUbicacion.text = "Ubicación GPS: %.4f, %.4f".format(lat, lon)
        } else {
            textoUbicacion.text = "Ubicación: No registrada"
        }

        // Categoría e imagen representativa
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

        // Sonido de motor
        mediaPlayer = MediaPlayer.create(this, sonido)
        mediaPlayer?.start()

        // Inicializamos el ViewModel con su Factory
        val marcaDao = AppDatabase.obtener(this).marcaInfoDao()
        val repository = WikipediaRepository(marcaDao)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetalleViewModel(repository) as T
            }
        })[DetalleViewModel::class.java]

        // Observamos los estados de la consulta online/offline
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is DetalleUiState.Loading -> {
                    pbCargandoMarca.visibility = View.VISIBLE
                    tvEstadoMarca.visibility = View.GONE
                    tvTituloMarcaWiki.visibility = View.GONE
                    tvHistoriaMarcaWiki.visibility = View.GONE
                }

                is DetalleUiState.Success -> {
                    pbCargandoMarca.visibility = View.GONE

                    if (state.esOffline) {
                        tvEstadoMarca.text = "📶 Modo sin conexión (datos cargados de caché local)"
                        tvEstadoMarca.visibility = View.VISIBLE
                    } else {
                        tvEstadoMarca.visibility = View.GONE
                    }

                    val info = state.marcaInfo
                    tvTituloMarcaWiki.text = info.titulo ?: marca
                    tvTituloMarcaWiki.visibility = View.VISIBLE

                    if (!info.descripcion.isNullOrEmpty()) {
                        tvHistoriaMarcaWiki.text = info.descripcion
                        tvHistoriaMarcaWiki.visibility = View.VISIBLE
                    } else {
                        tvHistoriaMarcaWiki.visibility = View.GONE
                    }
                }

                is DetalleUiState.Error -> {
                    pbCargandoMarca.visibility = View.GONE
                    tvTituloMarcaWiki.visibility = View.GONE
                    tvHistoriaMarcaWiki.visibility = View.GONE

                    tvEstadoMarca.text = "⚠️ ${state.mensaje}"
                    tvEstadoMarca.visibility = View.VISIBLE
                }
            }
        }

        // Cargamos la información de la marca desde Wikipedia/Room
        if (marca.isNotBlank()) {
            viewModel.cargarInfoMarca(marca)
        }

        botonVolver.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}