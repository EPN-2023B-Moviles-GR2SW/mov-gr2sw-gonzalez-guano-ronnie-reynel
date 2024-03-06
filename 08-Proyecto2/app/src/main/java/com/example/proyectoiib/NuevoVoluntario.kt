package com.example.proyectoiib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.firestore.FirebaseFirestore

class NuevoVoluntario : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_voluntario)

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

        val bntSiguiente = findViewById<Button>(R.id.unete)
        bntSiguiente.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.nombre_voluntario).text.toString()
            val apellido = findViewById<EditText>(R.id.apellido_voluntario).text.toString()
            val edad = findViewById<EditText>(R.id.edad_voluntario).text.toString()
            val telefono = findViewById<EditText>(R.id.telefono_voluntario).text.toString()
            val cedula = findViewById<EditText>(R.id.cedula_voluntario).text.toString()

            val datosVoluntario = hashMapOf(
                "nombre" to nombre,
                "apellido" to apellido,
                "edad" to edad,
                "telefono" to telefono,
                "cedula" to cedula,
            )

            db.collection("voluntarios")
                .add(datosVoluntario)
                .addOnSuccessListener {}
                .addOnFailureListener {
                }

            val botonHome = findViewById<LinearLayout>(R.id.home)
            botonHome.setOnClickListener {
                irActividad(MainActivity::class.java)
            }
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}