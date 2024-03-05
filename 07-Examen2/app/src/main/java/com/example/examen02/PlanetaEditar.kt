package com.example.examen02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen02.entidad.Planeta
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PlanetaEditar : AppCompatActivity() {
    var planeta = Planeta()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_editar)
        // Recupera el ID
        val intent = intent
        val idSistema = intent.getStringExtra("idSistema")
        val idPlaneta = intent.getStringExtra("idPlaneta")
        // Buscar Planeta
        mostrarSnackbar(idPlaneta!!)
        consultarDocumento(idSistema!!, idPlaneta!!)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre_planeta)
        val tipo = findViewById<EditText>(R.id.input_tipo)
        val distanciaAlSol = findViewById<EditText>(R.id.input_distancia)
        val esHabitable = findViewById<Switch>(R.id.input_habitable)

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_planeta)
        botonActualizar
            .setOnClickListener {
                planeta.nombre = nombre.text.toString()
                planeta.tipo = tipo.text.toString()
                planeta.distanciaAlSol = distanciaAlSol.text.toString().toDouble()
                planeta.esHabitable = esHabitable.isChecked
                actualizarDepartamento(planeta, idSistema!!)
            }
    }

    fun consultarDocumento(idSistema: String, idPlaneta: String) {
        val db = Firebase.firestore
        val departamentosRef = db.collection("sistema/${idSistema}/planetas")

        departamentosRef
            .document(idPlaneta)
            .get()
            .addOnSuccessListener {
                planeta = Planeta(
                    it.id as String?,
                    it.data?.get("nombre") as String,
                    it.data?.get("tipo") as String,
                    it.data?.get("distanciaAlSol") as Double,
                    it.data?.get("esHabitable") as Boolean
                )
                notificarActualizacionDepartamento()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }

    fun notificarActualizacionDepartamento() {
        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre_planeta)
        val tipo = findViewById<EditText>(R.id.input_tipo)
        val distanciaAlSol = findViewById<EditText>(R.id.input_distancia)
        val esHabitable = findViewById<Switch>(R.id.input_habitable)
        nombre.setText(planeta.nombre.toString())
        tipo.setText(planeta.tipo)
        distanciaAlSol.setText(planeta.distanciaAlSol.toString())
        esHabitable.isChecked = planeta.esHabitable
    }

    fun actualizarDepartamento(planeta: Planeta, idSistema: String) {
        val db = Firebase.firestore
        val planetasRef = db.collection("sistema/${idSistema}/planetas")

        // Crear un mapa con los nuevos datos del planeta
        val datosCondominio = hashMapOf(
            "nombre" to planeta.nombre,
            "tipo" to planeta.tipo,
            "distanciaAlSol" to planeta.distanciaAlSol,
            "esHabitable" to planeta.esHabitable,
        )
        // Actualizar el documento en Firestore
        planetasRef
            .document(planeta.id!!)
            .update(datosCondominio as Map<String, Any>)
            .addOnSuccessListener {
                mostrarSnackbar("Planeta Actualizado")
            }
            .addOnFailureListener {
                mostrarSnackbar("Error al actualizar el planeta")
            }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_planeta_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}