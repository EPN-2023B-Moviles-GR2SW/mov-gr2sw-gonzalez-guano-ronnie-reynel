package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackBar(texto: String){
        textoGlobal += texto
        val snack = Snackbar.make(findViewById(R.id.cl_ciclo_vida),
            textoGlobal, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackBar("Hola")
        mostrarSnackBar("onCreate")
    }

    override fun onStart(){
        super.onStart()
        mostrarSnackBar("onStart")
    }

    override fun onResume(){
        super.onResume()
        mostrarSnackBar("onResume")
    }

    override fun onRestart(){
        super.onRestart()
        mostrarSnackBar("onRestart")
    }

    override fun onPause(){
        super.onPause()
        mostrarSnackBar("onPause")
    }

    override fun onStop(){
        super.onStop()
        mostrarSnackBar("onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        mostrarSnackBar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            // Guardar Variables
            // Primitivos
            putString("textoGuardado", textoGlobal)
            // putInt ("numeroGuardado", numrto)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // RECUPERAR LAS VARIABLES
        // PRIMITIVOS
        val textoRecuperado:String? = savedInstanceState.getString("textoGuardado")
        // val textoRecuperado:Int? = savedInstanceState.getString("numeroGuardado")
        if(textoRecuperado != null){
            mostrarSnackBar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }

}