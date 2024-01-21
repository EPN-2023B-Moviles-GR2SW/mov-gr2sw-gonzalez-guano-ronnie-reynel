package com.example.a05_examen

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

class MainActivity : AppCompatActivity() {
    private lateinit var listViewSistemasSolares: ListView
    private lateinit var adaptador: ArrayAdapter<String>
    private val gestorEspacial = GestorEspacial()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewSistemasSolares = findViewById(R.id.lv_sistemas_solaress)
        actualizarListView()

        listViewSistemasSolares.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, PlanetasActivity::class.java)
            intent.putExtra("sistemaSolarId", position)
            startActivity(intent)
        }
    }

    private fun actualizarListView() {
        val sistemasSolares = gestorEspacial.obtenerSistemasSolares()
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, sistemasSolares)
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
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_modificar_sistema -> {
                mostrarSnackBar("${posicionItemSeleccionado}")
                return true
            }
            R.id.mi_eliminar_sistema -> {
                mostrarSnackBar("${posicionItemSeleccionado}")
                abrirDialogo()
                return true
            }
            R.id.mi_ver_planetas -> {
                mostrarSnackBar("${posicionItemSeleccionado}")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                mostrarSnackBar("Acepto ${which}")
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun mostrarSnackBar(texto: String){
        val snack = Snackbar.make(findViewById(R.id.lv_main),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }
}

