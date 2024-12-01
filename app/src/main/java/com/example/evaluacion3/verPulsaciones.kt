package com.example.evaluacion3

import android.os.Bundle
import android.widget.Adapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion3.Adapter.AdapterPulsacion
import com.example.evaluacion3.Models.Pulsacion
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
    private lateinit var pulsacionesList : ArrayList<Pulsacion>

    // Declarar adaptador
    private lateinit var adapterPulsacion: AdapterPulsacion

    //Recycler view
    private lateinit var pulsacionRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //ViewBinding
        binding = ActivityVerPulsacionesBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Mostrar datos en la pantalla
        pulsacionRecyclerView = binding.rvPulsaciones
        pulsacionRecyclerView.layoutManager = LinearLayoutManager(this)
        pulsacionRecyclerView.hasFixedSize()

        pulsacionesList = arrayListOf<Pulsacion>()



        getPulsaciones()


    }



    private fun getPulsaciones() {
        database = FirebaseDatabase.getInstance().getReference("Pulsaciones")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(pulsacionesSnapshot in snapshot.children){
                        val pulsacion = pulsacionesSnapshot.getValue(Pulsacion::class.java)
                        pulsacionesList.add(pulsacion!!)
                    }
                    adapterPulsacion = AdapterPulsacion(pulsacionesList)
                    pulsacionRecyclerView.adapter = adapterPulsacion
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}