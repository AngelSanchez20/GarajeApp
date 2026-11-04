
package com.example.garajeapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CocheAdapter(
    private val vehiculos: List<Vehiculo>,
    private val alBorrar: (Vehiculo) -> Unit
) : RecyclerView.Adapter<CocheAdapter.CocheViewHolder>() {

    class CocheViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.imagenFila)
        val textoMarcaModelo: TextView =
            view.findViewById(R.id.textoMarcaModeloFila)
        val textoPotencia: TextView =
            view.findViewById(R.id.textoPotenciaFila)
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

        // Pulsar una vez para ver los detalles
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleActivity::class.java)
            intent.putExtra("MARCA", vehiculo.marca)
            intent.putExtra("MODELO", vehiculo.modelo)
            intent.putExtra("POTENCIA", vehiculo.potencia)

            holder.itemView.context.startActivity(intent)
        }

        // Mantener pulsado para borrar el vehículo
        holder.itemView.setOnLongClickListener {
            alBorrar(vehiculo)
            true
        }
    }

    override fun getItemCount(): Int = vehiculos.size
}
