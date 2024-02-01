package com.example.a06_deber02

class Planeta(

    val id: Int,
    var nombre: String,
    var tipo: String,
    var distanciaAlSol: Double, // Decimal
    var esHabitable: Boolean
){
    override fun toString(): String {
        return "${nombre} - ${tipo} - ${distanciaAlSol}AU"
    }
}