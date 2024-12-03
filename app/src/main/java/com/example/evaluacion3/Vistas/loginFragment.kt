package com.example.evaluacion3.Vistas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.evaluacion3.RegistrarActivity
import com.example.evaluacion3.login
import com.example.evaluacion3.databinding.FragmentLoginBinding

class loginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            _binding = FragmentLoginBinding.inflate(inflater, container, false)

            // Configurar el botón para iniciar la actividad `login`
            binding.btnSi.setOnClickListener {
                try {
                    val intent = Intent(activity, login::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Mostrar mensaje de error en caso de falla al iniciar la actividad
                    activity?.let {
                        Toast.makeText(it, "Error al iniciar la actividad login: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            activity?.let {
                Toast.makeText(it, "Error al inflar el fragmento: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
