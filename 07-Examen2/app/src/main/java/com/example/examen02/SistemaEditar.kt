package com.example.examen02

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.entidad.SistemaSolar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SistemaEditar : AppCompatActivity() {
    lateinit var fechaDescubrimiento: EditText
    val calendar = Calendar.getInstance()
    var sistemaSolar = SistemaSolar()
    val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sistema_editar)
        // Recupera el ID
        val intent = intent
        val id = intent.getStringExtra("id")

        // Buscar Condominio
        mostrarSnackbar(id!!)
        consultarDocumento(id!!)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val numeroPlanetas = findViewById<EditText>(R.id.input_num_planetas)
        fechaDescubrimiento = findViewById<EditText>(R.id.input_fecha)
        val esMultiple= findViewById<Switch>(R.id.input_multiple)

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_planeta)
        botonActualizar
            .setOnClickListener {
                sistemaSolar.nombre = nombre.text.toString()
                sistemaSolar.numeroPlanetas = numeroPlanetas.text.toString()
                val fechaTexto = fechaDescubrimiento.text.toString()
                sistemaSolar.fechaDescubrimiento = LocalDate.parse(fechaTexto, formatoFecha)
                sistemaSolar.esMultiple = esMultiple.isChecked
                actualizarSistema(sistemaSolar)
            }
        fechaDescubrimiento
            .setOnClickListener {
                mostrarDatePickerDialog()
            }

    }

    fun consultarDocumento(id: String) {
        val db = Firebase.firestore
        val condominiosRef = db.collection("sistema")

        condominiosRef
            .document(id)
            .get()
            .addOnSuccessListener {
                sistemaSolar = SistemaSolar(
                    it.id as String?,
                    it.data?.get("nombre") as String,
                    it.data?.get("numeroPlanetas") as String,
                    (it.data?.get("fechaDescubrimiento") as Timestamp).toDate().toInstant().atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate(),
                    it.data?.get("esMultiple") as Boolean,
                )
                notificarActualizacionSistema()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }

    fun notificarActualizacionSistema() {
        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val numeroPlanetas = findViewById<EditText>(R.id.input_num_planetas)
        fechaDescubrimiento = findViewById<EditText>(R.id.input_fecha)
        val esMultiple = findViewById<Switch>(R.id.input_multiple)

        nombre.setText(sistemaSolar.nombre)
        numeroPlanetas.setText(sistemaSolar.numeroPlanetas)
        val fechaFormateada = sistemaSolar.fechaDescubrimiento.format(formatoFecha)
        fechaDescubrimiento.setText(fechaFormateada)
        esMultiple.isChecked = sistemaSolar.esMultiple
    }

    fun mostrarDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(year, monthOfYear, dayOfMonth)
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val fechaFormateada = formatoFecha.format(fechaSeleccionada.time)
                fechaDescubrimiento.setText(fechaFormateada)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun actualizarSistema(sistemaSolar: SistemaSolar) {
        val db = Firebase.firestore
        val sistemaRef = db.collection("sistema")

        val fechaTimestamp = Timestamp(
            Date.from(
                sistemaSolar.fechaDescubrimiento.atStartOfDay(ZoneId.systemDefault()).toInstant()
            )
        )
        // Crear un mapa con los nuevos datos del condominio
        val datosSistema = hashMapOf(
            "nombre" to sistemaSolar.nombre,
            "numeroPlanetas" to sistemaSolar.numeroPlanetas,
            "fechaDescubrimiento" to fechaTimestamp,
            "esMultiple" to sistemaSolar.esMultiple,
        )
        // Actualizar el documento en Firestore
        sistemaRef
            .document(sistemaSolar.id!!)
            .update(datosSistema as Map<String, Any>)
            .addOnSuccessListener {
                mostrarSnackbar("Sistema Solar Actualizado")
            }
            .addOnFailureListener {
                mostrarSnackbar("Error al actualizar el Sistema Solar")
            }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_sistema_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

}