package com.example.a05_examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class LVPlanetas : AppCompatActivity() {
    private lateinit var listViewPlanetas: ListView
    private val gestorEspacial = GestorEspacial()
    var posicionItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lvplanetas)

        val sistemaSolarId = intent.getIntExtra("ID_SISTEMA", -1)
        listViewPlanetas = findViewById(R.id.lv_planetas)
        actualizarListView(sistemaSolarId)

        val botonAñadirPlaneta = findViewById<Button>(R.id.btn_ir_agregar_planeta)
        botonAñadirPlaneta
            .setOnClickListener{
                val intent = Intent(this, AgregarPlaneta::class.java)
                intent.putExtra("ID_SISTEMA", sistemaSolarId) // Reemplaza idSistemaSolar con el ID real del sistema solar seleccionado
                startActivity(intent)
            }

        registerForContextMenu(listViewPlanetas)
    }

    private fun actualizarListView(sistemaSolarId: Int) {
        val planetas = gestorEspacial.obtenerPlanetasDelSistemaSolar(sistemaSolarId)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, planetas)
        listViewPlanetas.adapter = adaptador
        adaptador.notifyDataSetChanged() // Notificar al adaptador
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu2, menu)
        // Obtener el id del ArraylistSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position + 1
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_modificar_planeta -> {
                val sistemaSolarId = intent.getIntExtra("ID_SISTEMA", -1)
                val intent = Intent(this, ModificarPlaneta::class.java)
                intent.putExtra("ID_PlANETA", posicionItemSeleccionado) // Asegúrate de enviar el identificador correcto
                startActivity(intent)
                actualizarListView(sistemaSolarId)
                return true
            }
            R.id.mi_eliminar_planeta -> {
                val sistemaSolarId = intent.getIntExtra("ID_SISTEMA", -1)
                abrirDialogo(posicionItemSeleccionado)
                actualizarListView(sistemaSolarId)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val sistemaSolarId = intent.getIntExtra("ID_SISTEMA", -1)
        actualizarListView(sistemaSolarId)
    }

    fun abrirDialogo(idPlaneta: Int){
        val builder = AlertDialog.Builder(this)
        val sistemaSolarId = intent.getIntExtra("ID_SISTEMA", -1)
        builder.setTitle("Confirmar Eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar este planeta?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            gestorEspacial.eliminarPlaneta(idPlaneta)
            actualizarListView(sistemaSolarId)
            mostrarSnackBar("Planeta eliminado")
        }
        builder.setNegativeButton("Cancelar", null)

        val dialogo = builder.create()
        dialogo.show()
    }


    fun mostrarSnackBar(texto: String){
        val snack = Snackbar.make(findViewById(R.id.lv_main),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }
}