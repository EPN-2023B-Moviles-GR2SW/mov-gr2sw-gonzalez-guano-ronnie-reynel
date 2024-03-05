package com.example.examen02.entidad

import java.time.LocalDate

class SistemaSolar(

    val id: String?,
    var nombre: String,
    var numeroPlanetas: String,
    var fechaDescubrimiento: LocalDate,
    var esMultiple: Boolean = false,
){
    constructor() : this(null, "", "", LocalDate.now())
    override fun toString(): String {
        return "${nombre} - Multiple:${esMultiple}"
    }
}
