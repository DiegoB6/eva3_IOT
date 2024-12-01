package com.example.evaluacion3.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion3.Models.Pulsacion
import com.example.evaluacion3.R

class AdapterPulsacion(private var pulsaciones: ArrayList<Pulsacion> ):
    RecyclerView.Adapter<AdapterPulsacion.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {


        val pulso: TextView = itemView.findViewById(R.id.tvPulsaciones)
        val oxigeno: TextView = itemView.findViewById(R.id.tvOxigeno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPulsacion.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pulsaciones, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pulsacion = pulsaciones[position]

        holder.pulso.text = pulsacion.pulso
        holder.oxigeno.text = pulsacion.oxigeno
    }

    override fun getItemCount(): Int {
       return pulsaciones.size
    }
}