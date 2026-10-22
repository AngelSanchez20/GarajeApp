package com.example.garajeapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CocheAdapter(private val coches: List<Coche>) :
    RecyclerView.Adapter<CocheAdapter.CocheViewHolder>() {

    class CocheViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.imagenFila)
        val textoMarcaModelo: TextView = view.findViewById(R.id.textoMarcaModeloFila)
        val textoPotencia: TextView = view.findViewById(R.id.textoPotenciaFila)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocheViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.fila_coche, parent, false)
        return CocheViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CocheViewHolder, position: Int) {
        val coche = coches[position]
        holder.textoMarcaModelo.text = "${coche.marca} ${coche.modelo}"
        holder.textoPotencia.text = "${coche.potencia} CV"

        // Elegimos la imagen según la potencia
        val imagenCategoria = when {
            coche.potencia < 100 -> R.drawable.coche_utilitario
            coche.potencia <= 300 -> R.drawable.coche_deportivo
            else -> R.drawable.coche_super
        }
        holder.imagen.setImageResource(imagenCategoria)

        // Al pulsar la fila, abrimos la ficha y le mandamos los datos
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleActivity::class.java)
            intent.putExtra("MARCA", coche.marca)
            intent.putExtra("MODELO", coche.modelo)
            intent.putExtra("POTENCIA", coche.potencia)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = coches.size
}