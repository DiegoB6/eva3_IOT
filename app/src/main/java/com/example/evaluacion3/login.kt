package com.example.evaluacion3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluacion3.Vistas.loginFragment
import com.example.evaluacion3.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class login : AppCompatActivity() {

    // Configurar viewBinding
    private lateinit var binding: ActivityLoginBinding
    // Configurar firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()

            // Inicializar viewBinding
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                try {
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                    insets
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@login, "Error al configurar el sistema de barras: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                insets
            }


            // Inicializar Firebase Auth
            auth = Firebase.auth

            // Programar el botón de login
            binding.btnLogin.setOnClickListener {
                try {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()

                    if (email.isEmpty()) {
                        binding.etEmail.error = "Por favor ingrese un correo"
                        return@setOnClickListener
                    }

                    if (password.isEmpty()) {
                        binding.etPassword.error = "Por favor ingrese la contraseña"
                        return@setOnClickListener
                    }
                    signIn(email, password)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al intentar iniciar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // Programar el enlace para ir a Registro
            binding.tvRegistrar.setOnClickListener {
                try {
                    val intent = Intent(this, RegistrarActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al redirigir al registro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al inicializar la actividad: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()

                        // Ir al CRUD
                        try {
                            val intent = Intent(this, crud::class.java)
                            startActivity(intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this, "Error al redirigir al CRUD: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al procesar la autenticación: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
