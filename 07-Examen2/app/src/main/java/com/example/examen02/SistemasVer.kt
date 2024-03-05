package com.example.examen02

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.entidad.SistemaSolar
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.ZoneId
import java.util.Date

class SistemasVer : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idSistemaSeleccionado = ""
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<SistemaSolar>
    var query: Query? = null
    val arreglo: ArrayList<SistemaSolar> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sistemas_ver)
        listView = findViewById<ListView>(R.id.lv_sistema_ver)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arreglo
        )
        listView.adapter = adaptador
        //consultarColeccion()
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        val botonCrearSistema = findViewById<Button>(R.id.btn_crear_sistema)
        botonCrearSistema
            .setOnClickListener {
                crearSistema()
            }
    }

    fun consultarColeccion() {
        val db = Firebase.firestore
        val sistemasRef = db.collection("sistema")
        var tarea: Task<QuerySnapshot>? = null
        tarea = sistemasRef.get() // 1era vez
        limpiarArreglo()
        //adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, sistemasRef)
                    for (sistema in documentSnapshots) {
                        anadirAArreglo(sistema)
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
        val sistemaSolar = SistemaSolar(
            document.id as String?,
            document.data?.get("nombre") as String,
            document.data?.get("numeroPlanetas") as String,
            (document.data?.get("fechaDescubrimiento") as Timestamp).toDate().toInstant().atZone(
                ZoneId.systemDefault()
            ).toLocalDate(),
            document.data?.get("esMultiple") as Boolean,
        )
        arreglo.add(sistemaSolar)
    }

    fun crearSistema() {
        val db = Firebase.firestore
        val sistemaRef = db.collection("sistema")
        val datosSistema = hashMapOf(
            "nombre" to "Beta Centauri",
            "numeroPlanetas" to "2",
            "fechaDescubrimiento" to Timestamp.now(),
            "esMultiple" to false,
        )
        val identificador = Date().time
        sistemaRef // (crear/actualizar)
            .document(identificador.toString())
            .set(datosSistema)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_sistema, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Sistema en la posición seleccionada
        val sistemaSeleccionado = arreglo[posicion]
        // Obtener el id del Sistema seleccionado
        idSistemaSeleccionado = sistemaSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_sistema -> {
                irActividadConId(SistemaEditar::class.java, idSistemaSeleccionado)
                return true
            }

            R.id.mi_eliminar_sistema -> {
                abrirDialogo()
                return true
            }

            R.id.mi_ver_planetas -> {
                irActividadConId(PlanetasVer::class.java, idSistemaSeleccionado)
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
        intent.putExtra("id", id)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_sistema_ver),
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
            eliminarRegistro(idSistemaSeleccionado)
            mostrarSnackbar("Elemento id:${idSistemaSeleccionado} eliminado")
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarRegistro(id: String) {
        val db = Firebase.firestore
        val condominiosRef = db.collection("sistema")

        condominiosRef
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { }
            .addOnFailureListener { }
        consultarColeccion()
        //adaptador.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        consultarColeccion()
        // adaptador.notifyDataSetChanged()
    }
}