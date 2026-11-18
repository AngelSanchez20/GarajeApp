package com.example.garajeapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ListaActivity : ComponentActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var dao: VehiculoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        recycler = findViewById(R.id.recyclerCoches)
        val botonVolver = findViewById<Button>(R.id.botonVolverLista)

        recycler.layoutManager = LinearLayoutManager(this)

        dao = AppDatabase.obtener(this).vehiculoDao()

        botonVolver.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarVehiculos()
    }

    private fun cargarVehiculos() {
        lifecycleScope.launch {
            val vehiculos = dao.obtenerTodas()

            recycler.adapter = CocheAdapter(vehiculos) { vehiculo ->

                lifecycleScope.launch {
                    dao.borrar(vehiculo)

                    Toast.makeText(
                        this@ListaActivity,
                        "Vehículo eliminado",
                        Toast.LENGTH_SHORT
                    ).show()

                    cargarVehiculos()
                }
            }
        }
    }
}
