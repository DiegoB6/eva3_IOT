package com.example.evaluacion3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evaluacion3.Vistas.inicioFragment
import com.example.evaluacion3.Vistas.loginFragment
import com.example.evaluacion3.Vistas.manualFragment
import com.example.evaluacion3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // configuracion Viewbinding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // configuracion fragment
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                    inicioFragment()).commit()
        }


        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, inicioFragment()).commit()
                    true
                }

                R.id.item_2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, manualFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, loginFragment()).commit()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {
            when(it.itemId){
                R.id.item_1 -> {
                    true
                }

                R.id.item_2 -> {
                    true
                }
                R.id.item_3 -> {
                    true
                }
                else -> false
            }
        }

    }
}