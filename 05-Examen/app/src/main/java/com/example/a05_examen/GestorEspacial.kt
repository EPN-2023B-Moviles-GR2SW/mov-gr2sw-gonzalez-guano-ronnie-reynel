package com.example.a05_examen

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GestorEspacial() {

    private val sistemasSolares: MutableList<SistemaSolar> = mutableListOf()

    init {
        // Datos quemados
        sistemasSolares.add(
            SistemaSolar(
                id = 1,
                nombre = "Sistema Solar",
                fechaDescubrimiento = SimpleDateFormat("yyyy-MM-dd").parse("2024-01-20") ?: Date(),
                numeroPlanetas = 8,
                esMultiple = false,
                planetas = mutableListOf(
                    Planeta(id = 1, nombre = "Tierra", tipo = "Terrestre", distanciaAlSol = 1.0, esHabitable = true),
                    Planeta(id = 2, nombre = "Marte", tipo = "Terrestre", distanciaAlSol = 1.5, esHabitable = false)
                )
            )
        )
        sistemasSolares.add(
            SistemaSolar(
                id = 2,
                nombre = "Alpha Centauri",
                fechaDescubrimiento = SimpleDateFormat("yyyy-MM-dd").parse("2023-12-10") ?: Date(),
                numeroPlanetas = 2,
                esMultiple = true,
                planetas = mutableListOf(
                    Planeta(id = 3, nombre = "Proxima b", tipo = "Exoplaneta", distanciaAlSol = 0.5, esHabitable = true)
                )
            )
        )
        // Agrega más sistemas y planetas según sea necesario
    }

    fun agregarSistemaSolar(sistemaSolar: SistemaSolar) {
        sistemasSolares.add(sistemaSolar)
    }

    fun obtenerSistemasSolares(): List<SistemaSolar> {
        return sistemasSolares
    }

    fun agregarPlanetaASistemaSolar(idSistemaSolar: Int, planeta: Planeta) {
        sistemasSolares.find { it.id == idSistemaSolar }?.planetas?.add(planeta)
    }


    fun obtenerSistemaSolarPorId(id: Int): SistemaSolar? {
        return sistemasSolares.find { it.id == id }
    }

    fun obtenerPlanetaPorId(idPlaneta: Int): Planeta? {
        for (sistemaSolar in sistemasSolares) {
            for (planeta in sistemaSolar.planetas) {
                if (planeta.id == idPlaneta) {
                    return planeta
                }
            }
        }
        return null // Retorna null si no se encuentra el planeta
    }

    fun eliminarSistemaSolar(id: Int) {
        val sistemaAEliminar = sistemasSolares.find { it.id == id }
        if (sistemaAEliminar != null) {
            sistemasSolares.remove(sistemaAEliminar)
        }
    }

    fun actualizarPlaneta(idPlaneta: Int, nombrePlaneta: String, tipoPlaneta: String, distanciaAlSol: Double, esHabitable: Boolean) {
        // Buscar el sistema solar que contiene el planeta
        for (sistema in sistemasSolares) {
            val planeta = sistema.planetas.find { it.id == idPlaneta }
            planeta?.let {
                // Actualizar los datos del planeta
                it.nombre = nombrePlaneta
                it.tipo = tipoPlaneta
                it.distanciaAlSol = distanciaAlSol
                it.esHabitable = esHabitable
                return // Salir del método una vez que el planeta es encontrado y actualizado
            }
        }
        // Si el planeta no se encuentra, puedes manejarlo de alguna manera (opcional)
    }

    fun eliminarPlaneta(idPlaneta: Int) {
        sistemasSolares.forEach { sistemaSolar ->
            val planetaAEliminar = sistemaSolar.planetas.find { it.id == idPlaneta }
            if (planetaAEliminar != null) {
                sistemaSolar.planetas.remove(planetaAEliminar)
            }
        }
    }

    fun generarIdSistemaSolar(): Int {
        return (sistemasSolares.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun generarIdPlaneta(): Int {
        // Genera un ID único para un nuevo planeta
        val todosLosPlanetas = sistemasSolares.flatMap { it.planetas }
        return (todosLosPlanetas.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun obtenerPlanetasDelSistemaSolar(idSistemaSolar: Int): List<Planeta> {
        val sistemaSolarEncontrado = sistemasSolares.find { it.id == idSistemaSolar }
        return sistemaSolarEncontrado?.planetas ?: emptyList()
    }

}