package com.example.garajeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editMarca = findViewById<EditText>(R.id.editMarca)
        val editModelo = findViewById<EditText>(R.id.editModelo)
        val editPotencia = findViewById<EditText>(R.id.editPotencia)
        val botonAnadir = findViewById<Button>(R.id.botonAnadir)
        val botonVerGaraje = findViewById<Button>(R.id.botonVerGaraje)

        // Recordamos la última marca escrita
        val prefs = getSharedPreferences("MiGaraje", MODE_PRIVATE)
        editMarca.setText(prefs.getString("ULTIMA_MARCA", ""))

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
                    Toast.makeText(this, "La potencia tiene que ser un número", Toast.LENGTH_SHORT).show()
                }
                potencia <= 0 -> {
                    Toast.makeText(this, "La potencia tiene que ser mayor que 0", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    prefs.edit().putString("ULTIMA_MARCA", marca).apply()

                    Garaje.coches.add(Coche(marca, modelo, potencia))
                    Toast.makeText(this, "Coche añadido", Toast.LENGTH_SHORT).show()

                    // Vaciamos modelo y potencia para el siguiente coche
                    editModelo.setText("")
                    editPotencia.setText("")
                }
            }
        }

        botonVerGaraje.setOnClickListener {
            if (Garaje.coches.isEmpty()) {
                Toast.makeText(this, "Todavía no has añadido ningún coche", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, ListaActivity::class.java))
            }
        }
    }
}