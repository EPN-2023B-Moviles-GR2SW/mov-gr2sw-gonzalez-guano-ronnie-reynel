package com.example.proyectoiib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val botonHome = findViewById<LinearLayout>(R.id.home)
        botonHome.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        val botonMascotas = findViewById<LinearLayout>(R.id.mascotas)
        botonMascotas.setOnClickListener {
            irActividad(Mascotas::class.java)
        }
        val botonVoluntarios = findViewById<LinearLayout>(R.id.voluntarios)
        botonVoluntarios.setOnClickListener {
            irActividad(Voluntarios::class.java)
        }

    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

