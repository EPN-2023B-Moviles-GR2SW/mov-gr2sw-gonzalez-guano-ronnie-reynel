class GestorEspacial (private val sistemasSolares: MutableList<SistemaSolar>) {

    // Métodos CRUD para SistemaSolar
    fun agregarSistemaSolar(sistemaSolar: SistemaSolar) {
        sistemasSolares.add(sistemaSolar)
    }

    fun actualizarSistemaSolar(sistemaSolarActualizado: SistemaSolar) {
        val indice = sistemasSolares.indexOfFirst { it.id == sistemaSolarActualizado.id }
        if (indice != -1) {
            sistemasSolares[indice] = sistemaSolarActualizado
        }
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

    fun eliminarSistemaSolarPorId(id: Int): Boolean {
        val sistemaSolar = obtenerSistemaSolarPorId(id)
        return if (sistemaSolar != null) {
            sistemasSolares.remove(sistemaSolar)
            true
        } else {
            false
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
}

