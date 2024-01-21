import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {

    val sistemasSolares = leerSistemasSolaresDeArchivo("sistemas_solares.txt")
    val gestor = GestorEspacial(sistemasSolares)

    while (true) {
        println("Bienvenido al gestor del sistema solar. Seleccione una opción:")
        println("1. Agregar sistema solar")
        println("2. Listar sistemas solares")
        println("3. Actualizar sistema solar")
        println("4. Eliminar sistema solar")
        println("5. Salir")
        val opcion = readLine()!!

        when (opcion) {
            "1" -> {
                agregarSistemaSolar(gestor)
                guardarSistemasSolaresEnArchivo(sistemasSolares, "sistemas_solares.txt")
            }
            "2" -> {
                listarSistemasSolares(gestor)
            }
            "3" -> {
                actualizarSistemaSolar(gestor)
                guardarSistemasSolaresEnArchivo(sistemasSolares, "sistemas_solares.txt")
            }
            "4" -> {
                eliminarSistemaSolar(gestor)
                guardarSistemasSolaresEnArchivo(sistemasSolares, "sistemas_solares.txt")
            }
            "5" -> break
        }
    }
}
fun leerSistemasSolaresDeArchivo(nombreArchivo: String): MutableList<SistemaSolar> {
    val sistemasSolares = mutableListOf<SistemaSolar>()
    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    File(nombreArchivo).forEachLine { linea ->
        if (!linea.startsWith("  -")) { // Es un sistema solar
            val datos = linea.split(",")
            val sistemaSolar = SistemaSolar(
                id = datos[0].toInt(),
                nombre = datos[1],
                fechaDescubrimiento = formatoFecha.parse(datos[2]) ?: Date(),
                numeroPlanetas = datos[3].toInt(),
                esMultiple = datos[4].toBoolean()
            )
            sistemasSolares.add(sistemaSolar)
        } else { // Es un planeta
            val datosPlaneta = linea.trim().substring(2).split(",")
            val planeta = Planeta(
                id = datosPlaneta[0].toInt(),
                nombre = datosPlaneta[1],
                tipo = datosPlaneta[2],
                distanciaAlSol = datosPlaneta[3].toDouble(),
                esHabitable = datosPlaneta[4].toBoolean()
            )
            sistemasSolares.last().planetas.add(planeta)
        }
    }

    return sistemasSolares
}

fun guardarSistemasSolaresEnArchivo(sistemasSolares: List<SistemaSolar>, nombreArchivo: String) {
    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    File(nombreArchivo).bufferedWriter().use { out ->
        sistemasSolares.forEach { sistemaSolar ->
            out.write("${sistemaSolar.id},${sistemaSolar.nombre},${formatoFecha.format(sistemaSolar.fechaDescubrimiento)},${sistemaSolar.numeroPlanetas},${sistemaSolar.esMultiple}\n")
            sistemaSolar.planetas.forEach { planeta ->
                out.write("  - ${planeta.id},${planeta.nombre},${planeta.tipo},${planeta.distanciaAlSol},${planeta.esHabitable}\n")
            }
        }
    }
}

fun agregarSistemaSolar(gestor: GestorEspacial) {
    println("Ingrese el nombre del sistema solar:")
    val nombre = readLine()!!

    println("Ingrese la fecha de descubrimiento (formato YYYY-MM-DD):")
    val fechaDescubrimiento = readLine()!!
    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val fecha = try {
        formatoFecha.parse(fechaDescubrimiento) ?: Date()
    } catch (e: ParseException) {
        println("Formato de fecha inválido. Usando fecha actual.")
        Date()
    }

    println("Ingrese el número de planetas:")
    val numeroPlanetas = readLine()?.toIntOrNull() ?: 0

    println("¿Es un sistema solar multiple? (si/no):")
    val tieneVidaInput = readLine()
    val tieneVida = tieneVidaInput.equals("si", ignoreCase = true)

    // Crear el sistema solar
    val id = gestor.generarIdSistemaSolar() // Suponiendo que tienes una función para generar un ID único
    val sistemaSolar = SistemaSolar(id, nombre, fecha, numeroPlanetas, tieneVida)

    // Agregar el sistema solar al gestor
    gestor.agregarSistemaSolar(sistemaSolar)

    println("Sistema Solar agregado exitosamente.")

    println("¿Desea agregar planetas a este sistema solar? (si/no):")
    val respuesta = readLine()
    if (respuesta.equals("si", ignoreCase = true)) {
        var continuar = true
        while (continuar) {
            agregarPlaneta(gestor, sistemaSolar.id)

            println("¿Desea agregar otro planeta? (si/no):")
            continuar = readLine().equals("si", ignoreCase = true)
        }
    }
}

fun agregarPlaneta(gestor: GestorEspacial, idSistemaSolar: Int) {
    println("Ingrese el nombre del planeta:")
    val nombre = readLine()!!

    println("Ingrese el tipo de planeta (Ejemplo: Terrestre, Gaseoso):")
    val tipo = readLine()!!

    println("Ingrese la distancia al sol (en UA):")
    val distanciaAlSol = readLine()?.toDoubleOrNull() ?: 0.0

    println("¿Es habitable? (si/no):")
    val esHabitableInput = readLine()
    val esHabitable = esHabitableInput.equals("si", ignoreCase = true)

    // Crear el planeta
    val idPlaneta = gestor.generarIdPlaneta() // Asumiendo que tienes una función para generar un ID único para planetas
    val planeta = Planeta(idPlaneta, nombre, tipo, distanciaAlSol, esHabitable)

    // Agregar el planeta al sistema solar
    gestor.agregarPlanetaASistemaSolar(idSistemaSolar, planeta)

    println("Planeta agregado exitosamente al sistema solar.")
}

fun listarSistemasSolares(gestor: GestorEspacial) {
    val sistemas = gestor.obtenerSistemasSolares()
    if (sistemas.isEmpty()) {
        println("/nNo hay sistemas solares registrados.")
        return
    }

    sistemas.forEach { sistema ->
        println("\nSistema Solar: ${sistema.nombre} (ID: ${sistema.id})")
        println("Fecha de Descubrimiento: ${sistema.fechaDescubrimiento}")
        println("¿Es sistema solar multiple?: ${if (sistema.esMultiple) "Sí" else "No"}")
        println("Planetas:")
        if (sistema.planetas.isEmpty()) {
            println("  No hay planetas registrados en este sistema solar.")
        } else {
            sistema.planetas.forEach { planeta ->
                println("  - ${planeta.nombre} (ID: ${planeta.id})")
                println("    Tipo: ${planeta.tipo}, Distancia al Sol: ${planeta.distanciaAlSol} UA, Habitable: ${if (planeta.esHabitable) "Sí" else "No"}")
            }
        }
        println("-------\n")
    }
}

fun actualizarSistemaSolar(gestor: GestorEspacial) {
    println("Ingrese el ID del sistema solar a actualizar:")
    val id = readLine()?.toIntOrNull()

    if (id == null) {
        println("ID inválido.")
        return
    }

    val sistemaSolar = gestor.obtenerSistemaSolarPorId(id)
    if (sistemaSolar == null) {
        println("Sistema solar no encontrado.")
        return
    }

    println("Actualizando el sistema solar: ${sistemaSolar.nombre}")

    println("Ingrese el nuevo nombre del sistema solar (dejar en blanco para no cambiar):")
    val nuevoNombre = readLine()
    if (!nuevoNombre.isNullOrBlank()) {
        sistemaSolar.nombre = nuevoNombre
    }

    // Aquí puedes agregar más campos para actualizar según tus necesidades

    gestor.actualizarSistemaSolar(sistemaSolar)
    println("Sistema solar actualizado con éxito.")
}

fun eliminarSistemaSolar(gestor: GestorEspacial) {
    println("Ingrese el ID del sistema solar a eliminar:")
    val id = readLine()?.toIntOrNull()

    if (id == null) {
        println("ID inválido.")
        return
    }

    val exito = gestor.eliminarSistemaSolarPorId(id)
    if (exito) {
        println("Sistema solar eliminado exitosamente.")
    } else {
        println("Sistema solar no encontrado.")
    }
}
