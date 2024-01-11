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
    val fechaNacimiento: Date = Date()

    // Switch
    val estadocivilWhen = "C"
    when (estadocivilWhen){
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
    ):this{
        if(uno == null) 0 else uno,
        dos
    }
    constructor(
        uno: Int,
        dos: Int?,
    ):this(
        uno,
        if (uno == null) 0 else dos,
    )
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


