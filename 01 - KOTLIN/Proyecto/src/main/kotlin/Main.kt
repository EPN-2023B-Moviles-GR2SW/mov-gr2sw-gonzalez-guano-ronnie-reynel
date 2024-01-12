fun main(args: Array<String>){
    println("Hello World")

    // Variables inmutables (No Reasignables)
    val inmutable: String = "Ronnie";

    // Variables mutables (Reasignables)
    var mutable: String = "Gonzalez";
    mutable = "Guano"

    // Se puede obviar el  punto y coma ";"


    //Duck Typing
    var ejemploVariable = "Ronnie Gonzalez"
    val edadEjemplo: Int = 23
    ejemploVariable.trim()


    // Variables primitivas
    val nombreEstudiante: String = "Ronnie Gonzalez";
    val sueldo: Double = 0.5
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true
    // Clases Java
    // val fechaNacimiento: Date = Date()

    // Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("Error")
        }
    }

    //Condicionales
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    //Solo Parametro Requerido (no nombrado)
    calcularSueldo(10.00)
    //Solo Parametros nombrados
    calcularSueldo(bonoEspecial =  20.00, sueldo = 10.00, tasa = 14.00)
    //Parametros Requerido no nombrado y Opcional nombrado
    calcularSueldo(10.00, bonoEspecial = 20.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // ArregloEstatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    // Arreglo Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //For Each -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor Actual: ${valorActual}")
        }
    // it significa elemento iterado
    arregloDinamico.forEach{println(it)}

    arregloEstatico
        .forEachIndexed{ indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")

        }
    println(respuestaForEach)


    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviemos el nuevo valor de la iteración
    // 2) Nos devuelve un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map {valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }


    // Filter -> Filtrar el arreglo
    // Devolver una expresion (True o False)
    // Nuevo arreglo centrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter {valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno cumple?)
    // AND -> ALL (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico.any{it >5}
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico.all{it > 5}
    println(respuestaAll)

    // Reduce -> Valor Acumulado
    // En kotlin siempre empieza en 0
    val respuestaReduce = arregloDinamico
        .reduce{acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)
        }
    println(respuestaReduce)
    
}


abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}


abstract class Numeros ( // Constructor primario
    // Ejemplo
    // uno: Int, (Parametro (sin modificador de acceso))
    // private var uno: Int,  Propiedad Publico Calse numeros.uno
    // var uno: Int Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,
    protected val numeroUno: Int,
    protected val numeroDos: Int,
){
    // var cedula: string + "" (por defecto es PUBLIC)
    // private valorCalculado: Int = 0 (private)
    init { //bloque de constructor primario
        this.numeroUno; this.numeroDos  // this es opcional
        numeroUno; numeroDos;           // sin el "this" es lo mismo
        println("Inicializando")
    }
}

class Suma( // Constructor primario
    unoParametro: Int,
    dosParametro: Int,
): Numeros(unoParametro, dosParametro){ // Extendiendo y mandando los parametros (superclase)
    init { // bloque de codigo constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor(
        uno: Int?,
        dos: Int,
    ) : this(
        if(uno == null) 0 else uno,
        dos
    )
    constructor(
        uno: Int,
        dos: Int?,
    ) : this(
        uno,
        if (dos == null) 0 else dos
    )
    constructor(
        uno: Int?,
        dos: Int?
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    public fun sumar(): Int {
        val total = numeroUno+ numeroDos
        // Suma. agregarHistorial(total)
        agregarHistorial(total)
        return total
    }

    companion object { //Atributos y metodos "compartidos"
        // entre las isntancias
        val pi = 3.14

        fun elevarAlCuadrado (num: Int): Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}

//void -> unit
fun imprimirNombre(nombre: String): Unit{
    println("Nombre : ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null, //Opcional -> puede ser null
): Double{
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    } else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}


