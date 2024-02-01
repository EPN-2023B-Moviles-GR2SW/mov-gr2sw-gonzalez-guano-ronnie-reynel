package com.example.a06_deber02

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

class AgregarPlaneta : AppCompatActivity() {

    private lateinit var editTextNombrePlaneta: EditText
    private lateinit var editTextTipoPlaneta: EditText
    private lateinit var editTextDistanciaAlSol: EditText
    private lateinit var checkBoxEsHabitable: CheckBox

    private val gestorEspacial = GestorEspacial()

    val sistemaSolarId = intent.getIntExtra("ID_SISTEMA", -1)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_planeta)

        editTextNombrePlaneta = findViewById(R.id.et_nombre_agr_plan)
        editTextTipoPlaneta = findViewById(R.id.et_tipo_agr_plan)
        editTextDistanciaAlSol = findViewById(R.id.et_distancia_agr_plan)
        checkBoxEsHabitable = findViewById(R.id.cb_habitable_agr_plan)

        val btnAceptar = findViewById<Button>(R.id.btn_aceptar_agr_plan)
        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_agr_plan)

        btnAceptar.setOnClickListener {
            val nombrePlaneta = editTextNombrePlaneta.text.toString()
            val tipoPlaneta = editTextTipoPlaneta.text.toString()
            val distanciaAlSol = editTextDistanciaAlSol.text.toString().toIntOrNull() ?: 0
            val esHabitable = checkBoxEsHabitable.isChecked

            // Crear un nuevo sistema solar
            val nuevoPlaneta = Planeta(
                id = gestorEspacial.generarIdPlaneta(), // Asumiendo que el id es el siguiente en la secuencia
                nombre = nombrePlaneta,
                tipo = tipoPlaneta,
                distanciaAlSol = distanciaAlSol.toDouble(),
                esHabitable = esHabitable
            )

            gestorEspacial.agregarPlanetaASistemaSolar(sistemaSolarId,nuevoPlaneta)

            finish()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

}