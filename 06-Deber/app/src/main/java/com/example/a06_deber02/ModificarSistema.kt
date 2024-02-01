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

class ModificarSistema : AppCompatActivity() {

    private lateinit var editTextNombreSistema: EditText
    private lateinit var editTextFechaDescubrimiento: EditText
    private lateinit var editTextNumeroPlanetas: EditText
    private lateinit var checkBoxEsMultiple: CheckBox

    private val gestorEspacial = GestorEspacial()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_sistema)

        editTextNombreSistema = findViewById(R.id.et_nombre_mod_plan)
        editTextFechaDescubrimiento = findViewById(R.id.et_fecha_mod_sis)
        editTextNumeroPlanetas = findViewById(R.id.et_numero_mod_sis)
        checkBoxEsMultiple = findViewById(R.id.cb_habitable_mod_plan)

        val idSistema = intent.getIntExtra("ID_SISTEMA", -1)
        // Cargar datos del sistema solar usando idSistema

        val btnAceptar = findViewById<Button>(R.id.btn_aceptar_mod_sis)
        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_mod_sis)

        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        btnAceptar.setOnClickListener {
            val nombreSistema = editTextNombreSistema.text.toString()
            val fechaDescubrimiento = formato.parse(editTextFechaDescubrimiento.text.toString())
            val numeroPlanetas = editTextNumeroPlanetas.text.toString().toIntOrNull() ?: 0
            val esMultiple = checkBoxEsMultiple.isChecked

            val sistemaSolar = gestorEspacial.obtenerSistemaSolarPorId(idSistema)

            if (sistemaSolar != null) {
                sistemaSolar.nombre = nombreSistema
                sistemaSolar.fechaDescubrimiento = fechaDescubrimiento
                sistemaSolar.numeroPlanetas = numeroPlanetas
                sistemaSolar.esMultiple = esMultiple
            }

            gestorEspacial
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

}