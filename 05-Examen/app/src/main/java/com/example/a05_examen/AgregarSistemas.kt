package com.example.a05_examen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgregarSistemas : AppCompatActivity() {

    private lateinit var editTextNombreSistema: EditText
    private lateinit var editTextFechaDescubrimiento: EditText
    private lateinit var editTextNumeroPlanetas: EditText
    private lateinit var checkBoxEsMultiple: CheckBox

    private val gestorEspacial = GestorEspacial()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_sistema)

        editTextNombreSistema = findViewById(R.id.et_nombre_agr_sis)
        editTextFechaDescubrimiento = findViewById(R.id.et_fecha_agr_sis)
        editTextNumeroPlanetas = findViewById(R.id.et_numero_agr_sis)
        checkBoxEsMultiple = findViewById(R.id.cb_habitable_agr_sis)

        val btnAceptar = findViewById<Button>(R.id.btn_aceptar_agr_sis)
        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_agr_sis)

        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        btnAceptar.setOnClickListener {
            val nombreSistema = editTextNombreSistema.text.toString()
            val fechaDescubrimiento = formato.parse(editTextFechaDescubrimiento.text.toString()) ?: Date()
            val numeroPlanetas = editTextNumeroPlanetas.text.toString().toIntOrNull() ?: 0
            val esMultiple = checkBoxEsMultiple.isChecked

            // Crear un nuevo sistema solar
            val nuevoSistemaSolar = SistemaSolar(
                id = gestorEspacial.generarIdSistemaSolar(), // Asumiendo que el id es el siguiente en la secuencia
                nombre = nombreSistema,
                fechaDescubrimiento = fechaDescubrimiento,
                numeroPlanetas = numeroPlanetas,
                esMultiple = esMultiple
            )

            gestorEspacial.agregarSistemaSolar(nuevoSistemaSolar)

            setResult(Activity.RESULT_OK)
            finish()

            finish()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }
}