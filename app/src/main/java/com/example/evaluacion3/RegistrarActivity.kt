package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluacion3.databinding.ActivityRegistrarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrarActivity : AppCompatActivity() {

    // Configurar viewBinding
    private lateinit var binding: ActivityRegistrarBinding

    // Configurar firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()
            binding = ActivityRegistrarBinding.inflate(layoutInflater)
            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Funcionamiento flecha hacia atrás redirigiendo al login
            val flechaRegistrar: Button = findViewById(R.id.flechaRegistrar)
            flechaRegistrar.setOnClickListener {
                try {
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al redirigir al login: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // Inicializar Firebase Auth
            try {
                auth = Firebase.auth
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error al inicializar Firebase Auth: ${e.message}", Toast.LENGTH_SHORT).show()
            }

            binding.btnRegistrar.setOnClickListener {
                try {
                    // Obtener el correo
                    val email = binding.etEmail.text.toString()
                    val pass1 = binding.etPassword.text.toString()
                    val pass2 = binding.etPassword2.text.toString()

                    if (email.isEmpty()) {
                        binding.etEmail.error = "Por favor ingrese un correo"
                        return@setOnClickListener
                    }

                    if (pass1.isEmpty()) {
                        binding.etPassword.error = "Por favor ingrese una contraseña"
                        return@setOnClickListener
                    }

                    if (pass2.isEmpty()) {
                        binding.etPassword2.error = "Por favor ingrese la contraseña nuevamente"
                        return@setOnClickListener
                    }

                    // Validar que ambas contraseñas coincidan
                    if (pass1 != pass2) {
                        binding.etPassword2.error = "Las contraseñas no coinciden"
                        return@setOnClickListener
                    } else {
                        signUp(email, pass1)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al procesar el registro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al inicializar la actividad: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUp(email: String, pass1: String) {
        try {
            auth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error en el registro del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
