package com.example.proyectoiib

import CustomAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Mascotas : AppCompatActivity() {

    var adapter: CustomAdapter? = null
    var query: Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotas)


        query = FirebaseFirestore.getInstance().collection("mascotas")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = CustomAdapter(query!!)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

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

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter?.stopListening()
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}