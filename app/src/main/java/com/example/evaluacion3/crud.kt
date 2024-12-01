package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluacion3.Models.Pulsacion
import com.example.evaluacion3.databinding.ActivityCrudBinding
import com.example.evaluacion3.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class crud : AppCompatActivity() {

    // viewBinding
    private lateinit var binding: ActivityCrudBinding

    // Base de datos Firebase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCrudBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar base de datos
        database = FirebaseDatabase.getInstance().getReference("Pulsaciones")

        binding.btnGuardar.setOnClickListener{
            // Obtener los datos del formulario de pulsaciones
            val pulso = binding.etPulsaciones.text.toString()
            val oxigeno = binding.etOxigeno.text.toString()

            // Crea el id de forma automatica al insertar una pulsacion
            val id = database.child("Pulsaciones").push().key

            if(pulso.isEmpty()){
                binding.etPulsaciones.error = "Por favor ingresar una pulsacion"
                return@setOnClickListener
            }
            if(oxigeno.isEmpty()){
                binding.etOxigeno.error = "Por favor ingresar nivel de oxigeno en sangre"
                return@setOnClickListener
            }


            // variable que contiene los datos del constructor
            val pulsacion = Pulsacion(id, pulso, oxigeno)

            //el dato puede ser string o nulo
            database.child(id!!).setValue(pulsacion).addOnSuccessListener{
                binding.etPulsaciones.setText("")
                binding.etOxigeno.setText("")
                Snackbar.make(binding.root, "Datos Agregados", Snackbar.LENGTH_SHORT).show()
                //confirmacion al insertar dato
            }

        }

        binding.btnVer.setOnClickListener{
            val intent = Intent(this, verPulsaciones::class.java)
            startActivity(intent)
        }
    }
}