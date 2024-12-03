package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion3.Adapter.AdapterPulsacion
import com.example.evaluacion3.Models.Pulsacion
import com.example.evaluacion3.Vistas.loginFragment
import com.example.evaluacion3.databinding.ActivityVerPulsacionesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class verPulsaciones : AppCompatActivity() {

    // Inicializar ViewBinding
    private lateinit var binding: ActivityVerPulsacionesBinding

    // Inicializar FireBase database realtime
    private lateinit var database: DatabaseReference

    // Lista de productos
    private lateinit var pulsacionesList: ArrayList<Pulsacion>

    // Declarar adaptador
    private lateinit var adapterPulsacion: AdapterPulsacion

    // Recycler view
    private lateinit var pulsacionRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()

            // ViewBinding
            binding = ActivityVerPulsacionesBinding.inflate(layoutInflater)
            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Funcionamiento flecha hacia atrás redirige al CRUD
            val flechaVer: Button = findViewById(R.id.flechaVerPulsaciones)
            flechaVer.setOnClickListener {
                try {
                    val intent = Intent(this, crud::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al redirigir: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // Configurar RecyclerView
            pulsacionRecyclerView = binding.rvPulsaciones
            pulsacionRecyclerView.layoutManager = LinearLayoutManager(this)
            pulsacionRecyclerView.setHasFixedSize(true)

            pulsacionesList = arrayListOf()

            getPulsaciones()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al inicializar la actividad: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPulsaciones() {
        try {
            database = FirebaseDatabase.getInstance().getReference("Pulsaciones")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            for (pulsacionesSnapshot in snapshot.children) {
                                val pulsacion = pulsacionesSnapshot.getValue(Pulsacion::class.java)
                                pulsacionesList.add(pulsacion!!)
                            }
                            adapterPulsacion = AdapterPulsacion(pulsacionesList)
                            pulsacionRecyclerView.adapter = adapterPulsacion
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@verPulsaciones, "Error al procesar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    try {
                        TODO("Not yet implemented")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@verPulsaciones, "Error al cancelar la operación: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al obtener los datos: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}