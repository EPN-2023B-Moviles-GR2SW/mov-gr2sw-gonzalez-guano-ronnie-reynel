import java.util.*

class SistemaSolar(

    val id: Int,
    var nombre: String,
    var fechaDescubrimiento: Date,
    var numeroPlanetas: Int,
    var esMultiple: Boolean,
    val planetas: MutableList<Planeta> = mutableListOf()
)