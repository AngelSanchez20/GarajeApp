package com.example.garajeapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCoches)
        val botonVolver = findViewById<Button>(R.id.botonVolverLista)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CocheAdapter(Garaje.coches)

        botonVolver.setOnClickListener {
            finish()
        }
    }
}