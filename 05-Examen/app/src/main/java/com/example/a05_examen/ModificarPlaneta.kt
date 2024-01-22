package com.example.a05_examen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ModificarPlaneta : AppCompatActivity() {

    private lateinit var editTextNombrePlaneta: EditText
    private lateinit var editTextTipoPlaneta: EditText
    private lateinit var editTextDistanciaAlSol: EditText
    private lateinit var checkBoxEsHabitable: CheckBox

    private val gestorEspacial = GestorEspacial()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_planeta)

        // Inicializar vistas
        editTextNombrePlaneta = findViewById(R.id.et_nombre_mod_plan)
        editTextTipoPlaneta = findViewById(R.id.et_tipo_mod_plan)
        editTextDistanciaAlSol = findViewById(R.id.et_distancia_mod_plan)
        checkBoxEsHabitable = findViewById(R.id.cb_habitable_mod_plan)

        // Obtener ID del planeta
        val idPlaneta = intent.getIntExtra("ID_PLANETA", -1)

        // Cargar datos del planeta
        cargarDatosDelPlaneta(idPlaneta)

        // Botones
        val btnAceptar = findViewById<Button>(R.id.btn_aceptar_mod_plan)
        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_mod_plan)

        btnAceptar.setOnClickListener {
            actualizarPlaneta(idPlaneta)
            finish()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun cargarDatosDelPlaneta(idPlaneta: Int) {
        val planeta = gestorEspacial.obtenerPlanetaPorId(idPlaneta)
        planeta?.let {
            editTextNombrePlaneta.setText(it.nombre)
            editTextTipoPlaneta.setText(it.tipo)
            editTextDistanciaAlSol.setText(it.distanciaAlSol.toString())
            checkBoxEsHabitable.isChecked = it.esHabitable
        }
    }

    private fun actualizarPlaneta(idPlaneta: Int) {
        val nombrePlaneta = editTextNombrePlaneta.text.toString()
        val tipoPlaneta = editTextTipoPlaneta.text.toString()
        val distanciaAlSol = editTextDistanciaAlSol.text.toString().toDoubleOrNull() ?: 0.0
        val esHabitable = checkBoxEsHabitable.isChecked

        gestorEspacial.actualizarPlaneta(idPlaneta, nombrePlaneta, tipoPlaneta, distanciaAlSol, esHabitable)
    }
}
