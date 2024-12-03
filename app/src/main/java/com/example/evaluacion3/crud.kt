package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluacion3.Models.Pulsacion
import com.example.evaluacion3.databinding.ActivityCrudBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class crud : AppCompatActivity() {

    // viewBinding
    private lateinit var binding: ActivityCrudBinding

    // Base de datos Firebase
    private lateinit var database: DatabaseReference

    // Configurar Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCrudBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            try {
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al configurar el sistema de barras: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            insets // Asegúrate de devolver el objeto insets aquí
        }

        // Inicializar Firebase Auth
        auth = Firebase.auth

        binding.btnLogout.setOnClickListener {
            try {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Estas seguro que deseas cerrar sesión?")
                    .setNeutralButton("Cancelar") { dialog, which ->
                        // No hacer nada, solo cerrar el dialogo
                    }
                    .setPositiveButton("Aceptar") { dialog, which ->
                        signOut() // Cerrar sesión
                    }
                    .show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al abrir el diálogo de cierre de sesión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Inicializar base de datos
        database = FirebaseDatabase.getInstance().getReference("Pulsaciones")

        binding.btnGuardar.setOnClickListener{
            try {
                // Obtener los datos del formulario de pulsaciones
                val pulso = binding.etPulsaciones.text.toString()
                val oxigeno = binding.etOxigeno.text.toString()

                // Crea el id de forma automatica al insertar una pulsacion
                val id = database.child("Pulsaciones").push().key

                if (pulso.isEmpty()) {
                    binding.etPulsaciones.error = "Por favor ingresar una pulsacion"
                    return@setOnClickListener
                }
                if (oxigeno.isEmpty()) {
                    binding.etOxigeno.error = "Por favor ingresar nivel de oxigeno en sangre"
                    return@setOnClickListener
                }

                // variable que contiene los datos del constructor
                val pulsacion = Pulsacion(id, pulso, oxigeno)

                //el dato puede ser string o nulo
                database.child(id!!).setValue(pulsacion).addOnSuccessListener {
                    binding.etPulsaciones.setText("")
                    binding.etOxigeno.setText("")
                    Snackbar.make(binding.root, "Datos Agregados", Snackbar.LENGTH_SHORT).show()
                    //confirmacion al insertar dato
                }.addOnFailureListener {
                    Toast.makeText(this, "Error al agregar datos: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al guardar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuracion del boton Ver Pulsaciones llevando al activity correspondiente
        binding.btnVer.setOnClickListener{
            try {
                val intent = Intent(this, verPulsaciones::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al abrir la actividad de pulsaciones: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón para cambiar la contraseña
        binding.btnCambiarPassword.setOnClickListener {
            try {
                val intent = Intent(this, cambiarPassword::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al abrir la actividad de cambio de contraseña: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signOut() {
        try {
            Firebase.auth.signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpia la Activity
            startActivity(intent)

            finish()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cerrar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
