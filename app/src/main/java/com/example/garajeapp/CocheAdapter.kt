package com.example.garajeapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CocheAdapter(
    private val vehiculos: List<Vehiculo>,
    private val alBorrar: (Vehiculo) -> Unit
) : RecyclerView.Adapter<CocheAdapter.CocheViewHolder>() {

    class CocheViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.imagenFila)
        val imagenFoto: ImageView = view.findViewById(R.id.imagenFotoFila)
        val textoMarcaModelo: TextView = view.findViewById(R.id.textoMarcaModeloFila)
        val textoPotencia: TextView = view.findViewById(R.id.textoPotenciaFila)
        val textoUbicacion: TextView = view.findViewById(R.id.textoUbicacionFila)
        val btnEliminar: ImageButton =
            view.findViewById(R.id.btnEliminarCoche) // Botón de papelera en la fila
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CocheViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.fila_coche, parent, false)

        return CocheViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CocheViewHolder, position: Int) {
        val vehiculo = vehiculos[position]

        holder.textoMarcaModelo.text = "${vehiculo.marca} ${vehiculo.modelo}"
        holder.textoPotencia.text = "${vehiculo.potencia} CV"

        val imagenCategoria = when {
            vehiculo.potencia < 100 -> R.drawable.coche_utilitario
            vehiculo.potencia <= 300 -> R.drawable.coche_deportivo
            else -> R.drawable.coche_super
        }
        holder.imagen.setImageResource(imagenCategoria)

        val bitmap = Fotos.cargar(vehiculo.fotoPath)
        if (bitmap != null) {
            holder.imagenFoto.setImageBitmap(bitmap)
            holder.imagenFoto.visibility = View.VISIBLE
        } else {
            holder.imagenFoto.visibility = View.GONE
        }

        if (vehiculo.latitud != null && vehiculo.longitud != null) {
            holder.textoUbicacion.text =
                "GPS: %.4f, %.4f".format(vehiculo.latitud, vehiculo.longitud)
            holder.textoUbicacion.visibility = View.VISIBLE
        } else {
            holder.textoUbicacion.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleActivity::class.java).apply {
                putExtra("MARCA", vehiculo.marca)
                putExtra("MODELO", vehiculo.modelo)
                putExtra("POTENCIA", vehiculo.potencia)
                putExtra("FOTO_PATH", vehiculo.fotoPath)
                val lat = vehiculo.latitud
                val lon = vehiculo.longitud
                if (lat != null && lon != null) {
                    putExtra("LATITUD", lat)
                    putExtra("LONGITUD", lon)
                }
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.btnEliminar.setOnClickListener {
            alBorrar(vehiculo)
        }
    }

    override fun getItemCount(): Int = vehiculos.size
}