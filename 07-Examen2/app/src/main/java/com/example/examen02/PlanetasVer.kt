package com.example.examen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.examen02.entidad.Planeta
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class PlanetasVer : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idPlanetaSeleccionado = ""
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Planeta>
    var query: Query? = null
    val arreglo: ArrayList<Planeta> = arrayListOf()
    var idSistema: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planetas_ver)
        // Recupera el ID
        val intent = intent
        // Buscar Planetas
        buscarPlaneta(intent.getStringExtra("id")!!)
        val botonCrearPlaneta = findViewById<Button>(R.id.btn_crear_planeta)
        botonCrearPlaneta
            .setOnClickListener {
                crearPlaneta()
            }
    }

    fun crearPlaneta() {
        if (idSistema == null) {
            return
        }
        val db = Firebase.firestore
        val planetasRef = db.collection("sistema/${idSistema}/planetas")
        val datosPlaneta = hashMapOf(
            "nombre" to "Alpha Centauri II",
            "tipo" to "Terrestre",
            "distanciaAlSol" to 8.6,
            "esHabitable" to true
        )
        val identificador = Date().time
        planetasRef // (crear/actualizar)
            .document(identificador.toString())
            .set(datosPlaneta)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }

    fun buscarPlaneta(id: String) {
        val db = Firebase.firestore
        val sistemaRef = db.collection("sistema")

        sistemaRef
            .document(id)
            .get()
            .addOnSuccessListener {
                idSistema = id
                val nombreSistema = findViewById<TextView>(R.id.tv_nombre_planeta)
                nombreSistema.setText(it.data?.get("nombre") as String)

                listView = findViewById<ListView>(R.id.lv_planeta_ver)
                adaptador = ArrayAdapter(
                    this, // Contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    arreglo
                )
                listView.adapter = adaptador
                consultarColeccion()
                registerForContextMenu(listView)
            }
            .addOnFailureListener { }
    }

    fun consultarColeccion() {
        if (idSistema == null) {
            return
        }
        val db = Firebase.firestore
        val planetasRef = db.collection("sistema/${idSistema}/planetas")
        var tarea: Task<QuerySnapshot>? = null
        tarea = planetasRef.get() // 1era vez
        limpiarArreglo()
        //adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, planetasRef)
                    for (planeta in documentSnapshots) {
                        anadirAArreglo(planeta)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener { }
        }
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        ref: Query
    ) {
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = ref
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun anadirAArreglo(
        document: QueryDocumentSnapshot
    ) {
        val planeta = Planeta(
            document.id as String?,
            document.data?.get("nombre") as String,
            document.data?.get("tipo") as String,
            document.data?.get("distanciaAlSol") as Double,
            document.data?.get("esHabitable") as Boolean
        )
        arreglo.add(planeta)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_planeta, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Planeta en la posición seleccionada
        val planetaSeleccionado = arreglo[posicion]
        // Obtener el id del Planeta seleccionado
        idPlanetaSeleccionado = planetaSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_planeta -> {
                irActividadConId(PlanetaEditar::class.java, idPlanetaSeleccionado)
                return true
            }

            R.id.mi_eliminar_planeta -> {
                abrirDialogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividadConId(
        clase: Class<*>,
        id: String
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("idSistema", idSistema);
        intent.putExtra("idPlaneta", id);
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_planeta_ver),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            eliminarRegistro(idPlanetaSeleccionado)
            mostrarSnackbar("Elemento id:${idPlanetaSeleccionado} eliminado")
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarRegistro(id: String) {
        if (idSistema == null) {
            return
        }
        val db = Firebase.firestore
        val departamentosRef = db.collection("sistema/${idSistema}/planetas")

        departamentosRef
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }

    override fun onResume() {
        super.onResume()
        consultarColeccion()
    }

}