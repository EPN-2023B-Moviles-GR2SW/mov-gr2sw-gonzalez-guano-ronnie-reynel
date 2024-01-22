package com.example.b2023gr2sw

class BBaseDatosMemoria {
    companion object{
            val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Ronnie", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Reynel", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Melany", "c@c.com")
                )
        }
    }
}