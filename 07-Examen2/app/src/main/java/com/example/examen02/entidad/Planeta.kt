package com.example.examen02.entidad

class Planeta(
    val id: String?,
    var nombre: String,
    var tipo: String,
    var distanciaAlSol: Double, // Decimal
    var esHabitable: Boolean = false
){

    constructor() : this(null, "", "", 0.0, false)
    override fun toString(): String {
        return "${nombre}"
    }
}
