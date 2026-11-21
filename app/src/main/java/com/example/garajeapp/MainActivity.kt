package com.example.garajeapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : ComponentActivity() {

    private var fotoPath: String? = null
    private var archivoFotoActual: File? = null
    private var latitud: Double? = null
    private var longitud: Double? = null

    private lateinit var clienteUbicacion: FusedLocationProviderClient
    private lateinit var ivFoto: ImageView
    private lateinit var tvUbicacion: TextView

    private val pedirPermisoCamara = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            lanzarCamara()
        } else {
            Toast.makeText(this, "Sin permiso no puedo hacer la foto", Toast.LENGTH_SHORT).show()
        }
    }

    private val hacerFoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exito ->
        val archivo = archivoFotoActual
        if (exito && archivo != null) {
            fotoPath = archivo.absolutePath
            val bitmap = Fotos.cargar(fotoPath)
            if (bitmap != null) {
                ivFoto.setImageBitmap(bitmap)
            }
        } else {
            archivo?.delete()
        }
    }

    private val elegirDeGaleria = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            val ruta = Fotos.copiarDesdeUri(this, uri)
            if (ruta != null) {
                fotoPath = ruta
                val bitmap = Fotos.cargar(ruta)
                if (bitmap != null) {
                    ivFoto.setImageBitmap(bitmap)
                }
            }
        }
    }

    private val pedirPermisosUbicacion = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        val fina = resultado[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val aprox = resultado[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (fina || aprox) {
            obtenerUbicacion()
        } else {
            Toast.makeText(this, "Sin permiso no puedo saber dónde estás", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos el servicio de localización de Google
        clienteUbicacion = LocationServices.getFusedLocationProviderClient(this)

        val dao = AppDatabase.obtener(this).vehiculoDao()

        val editMarca = findViewById<EditText>(R.id.editMarca)
        val editModelo = findViewById<EditText>(R.id.editModelo)
        val editPotencia = findViewById<EditText>(R.id.editPotencia)
        ivFoto = findViewById(R.id.ivFoto)
        tvUbicacion = findViewById(R.id.tvUbicacion)

        val btnCamara = findViewById<Button>(R.id.btnCamara)
        val btnGaleria = findViewById<Button>(R.id.btnGaleria)
        val btnUbicacion = findViewById<Button>(R.id.btnUbicacion)
        val botonAnadir = findViewById<Button>(R.id.botonAnadir)
        val botonVerGaraje = findViewById<Button>(R.id.botonVerGaraje)

        val prefs = getSharedPreferences("MiGaraje", MODE_PRIVATE)
        editMarca.setText(prefs.getString("ULTIMA_MARCA", ""))

        btnCamara.setOnClickListener { pulsarBotonFoto() }
        btnGaleria.setOnClickListener {
            elegirDeGaleria.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
        btnUbicacion.setOnClickListener { pulsarBotonUbicacion() }

        botonAnadir.setOnClickListener {
            val marca = editMarca.text.toString().trim()
            val modelo = editModelo.text.toString().trim()
            val potenciaTexto = editPotencia.text.toString().trim()
            val potencia = potenciaTexto.toIntOrNull()

            when {
                marca.isEmpty() -> {
                    Toast.makeText(this, "Escribe la marca del coche", Toast.LENGTH_SHORT).show()
                }

                modelo.isEmpty() -> {
                    Toast.makeText(this, "Escribe el modelo del coche", Toast.LENGTH_SHORT).show()
                }

                potenciaTexto.isEmpty() -> {
                    Toast.makeText(this, "Escribe la potencia del coche", Toast.LENGTH_SHORT).show()
                }

                potencia == null -> {
                    Toast.makeText(this, "La potencia tiene que ser un número", Toast.LENGTH_SHORT)
                        .show()
                }

                potencia <= 0 -> {
                    Toast.makeText(
                        this,
                        "La potencia tiene que ser mayor que 0",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    prefs.edit().putString("ULTIMA_MARCA", marca).apply()

                    lifecycleScope.launch {
                        val vehiculo = Vehiculo(
                            marca = marca,
                            modelo = modelo,
                            potencia = potencia,
                            latitud = latitud,
                            longitud = longitud,
                            fotoPath = fotoPath
                        )

                        dao.insertar(vehiculo)

                        Toast.makeText(this@MainActivity, "Coche añadido", Toast.LENGTH_SHORT)
                            .show()

                        editModelo.setText("")
                        editPotencia.setText("")

                        fotoPath = null
                        archivoFotoActual = null
                        latitud = null
                        longitud = null
                        ivFoto.setImageDrawable(null)
                        tvUbicacion.text = "Ubicación no obtenida"
                    }
                }
            }
        }

        botonVerGaraje.setOnClickListener {
            lifecycleScope.launch {
                val coches = dao.obtenerTodas()
                if (coches.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Todavía no has añadido ningún coche",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    startActivity(Intent(this@MainActivity, ListaActivity::class.java))
                }
            }
        }
    }

    private fun pulsarBotonFoto() {
        val permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permiso == PackageManager.PERMISSION_GRANTED) {
            lanzarCamara()
        } else {
            pedirPermisoCamara.launch(Manifest.permission.CAMERA)
        }
    }

    private fun lanzarCamara() {
        val archivo = Fotos.crearArchivo(this)
        archivoFotoActual = archivo
        hacerFoto.launch(Fotos.uriDe(this, archivo))
    }

    private fun tienePermisoUbicacion(): Boolean {
        val fina = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val aprox =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return fina == PackageManager.PERMISSION_GRANTED || aprox == PackageManager.PERMISSION_GRANTED
    }

    private fun pulsarBotonUbicacion() {
        if (tienePermisoUbicacion()) {
            obtenerUbicacion()
        } else {
            pedirPermisosUbicacion.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        clienteUbicacion
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { ubicacion: Location? ->
                if (ubicacion != null) {
                    latitud = ubicacion.latitude
                    longitud = ubicacion.longitude
                    tvUbicacion.text =
                        "Ubicación: %.4f, %.4f".format(ubicacion.latitude, ubicacion.longitude)
                } else {
                    Toast.makeText(this, "No se ha podido obtener la ubicación", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}
