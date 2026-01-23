package org.iesra

data class Libro(
    val titulo: String,
    val autor: String,
    val numPaginas: Int,
    var calificacion: Int
) {
    init {
        if (calificacion !in 0..10) calificacion = 0
    }
}

class ConjuntoLibros(capacidad: Int) {
    private val coleccion = arrayOfNulls<Libro>(capacidad)

    fun añadirLibro(libro: Libro): Boolean {
        val indice = coleccion.indexOfFirst { it == null }
        return if (indice != -1) {
            coleccion[indice] = libro
            true
        } else false
    }

    fun eliminarPorTitulo(titulo: String) {
        for (i in coleccion.indices) {
            if (coleccion[i]?.titulo?.equals(titulo, ignoreCase = true) == true) {
                coleccion[i] = null
            }
        }
    }

    fun eliminarPorAutor(autor: String) {
        for (i in coleccion.indices) {
            if (coleccion[i]?.autor?.equals(autor, ignoreCase = true) == true) {
                coleccion[i] = null
            }
        }
    }

    fun mostrarMayorMenorCalificacion() {
        val libros = coleccion.filterNotNull()
        if (libros.isNotEmpty()) {
            println("Mayor calificación: ${libros.maxByOrNull { it.calificacion }}")
            println("Menor calificación: ${libros.minByOrNull { it.calificacion }}")
        }
    }

    fun mostrarContenido() {
        val libros = coleccion.filterNotNull()
        if (libros.isEmpty()) println("Conjunto vacío")
        else libros.forEach { println(it) }
    }
}

fun main() {
    val conjunto = ConjuntoLibros(5)

    val l1 = Libro("Cien años de soledad", "García Márquez", 471, 10)
    val l2 = Libro("El Quijote", "Cervantes", 1000, 9)

    conjunto.añadirLibro(l1)
    conjunto.añadirLibro(l2)

    conjunto.eliminarPorTitulo("Cien años de soledad")
    conjunto.eliminarPorAutor("Cervantes")

    conjunto.añadirLibro(Libro("1984", "George Orwell", 328, 8))

    conjunto.mostrarContenido()
}