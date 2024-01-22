package com.example.a05_examen

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a05_examen.ui.theme._05ExamenTheme
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var listViewSistemasSolares: ListView
    private val gestorEspacial = GestorEspacial()
    var posicionItemSeleccionado = 0
    val REQUEST_CODE_ADD_SYSTEM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewSistemasSolares = findViewById(R.id.lv_sistemas_solares)
        actualizarListView()

        val botonAgregarSistemas= findViewById<Button>(R.id.btn_ir_agregar)
        botonAgregarSistemas
            .setOnClickListener{
                irActividad(AgregarSistemas::class.java)
                startActivityForResult(intent, REQUEST_CODE_ADD_SYSTEM)
                actualizarListView()
            }

        registerForContextMenu(listViewSistemasSolares)
    }

    private fun actualizarListView() {
        val sistemasSolares1 = gestorEspacial.obtenerSistemasSolares()
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, sistemasSolares1)
        listViewSistemasSolares.adapter = adaptador
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        // Obtener el id del ArraylistSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position + 1
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_modificar_sistema -> {
                val intent = Intent(this, ModificarSistema::class.java)
                intent.putExtra("ID_SISTEMA", posicionItemSeleccionado) // Asegúrate de enviar el identificador correcto
                startActivity(intent)
                actualizarListView()
                return true
            }
            R.id.mi_eliminar_sistema -> {
                abrirDialogo(posicionItemSeleccionado)
                actualizarListView()
                return true
            }
            R.id.mi_ver_planetas -> {
                val intent = Intent(this, LVPlanetas::class.java)
                intent.putExtra("ID_SISTEMA", posicionItemSeleccionado) // Asegúrate de enviar el identificador correcto
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(idSistema: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar Eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar este sistema solar?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            gestorEspacial.eliminarSistemaSolar(idSistema)
            actualizarListView()
            mostrarSnackBar("Sistema Solar eliminado")
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

    override fun onResume() {
        super.onResume()
        actualizarListView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD_SYSTEM && resultCode == Activity.RESULT_OK) {
            actualizarListView()
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

