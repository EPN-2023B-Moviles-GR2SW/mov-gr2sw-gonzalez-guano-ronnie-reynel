package com.example.a06_deber02

import java.util.Date

class SistemaSolar(

    val id: Int,
    var nombre: String,
    var fechaDescubrimiento: Date,
    var numeroPlanetas: Int,
    var esMultiple: Boolean,
    val planetas: MutableList<Planeta> = mutableListOf()
){
    override fun toString(): String {
        return "${nombre} - Multiple:${esMultiple}"
    }
}