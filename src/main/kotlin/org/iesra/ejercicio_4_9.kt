package org.iesra

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Tarea(
    val id: Int,
    val descripcion: String,
    var realizada: Boolean = false,
    var fechaFinalizacion: String? = null
) {
    fun marcarComoRealizada() {
        realizada = true
        val ahora = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        fechaFinalizacion = ahora.format(formatter)
    }

    override fun toString(): String {
        val estado = if (realizada) "Realizada ($fechaFinalizacion)" else "Pendiente"
        return "ID: $id | Desc: $descripcion | Estado: $estado"
    }
}

class GestorTareas {
    private val listaTareas = mutableListOf<Tarea>()
    private var contadorId = 1

    fun agregarTarea(descripcion: String) {
        listaTareas.add(Tarea(contadorId++, descripcion))
        println("Tarea agregada con éxito.")
    }

    fun eliminarTarea(id: Int) {
        if (listaTareas.removeIf { it.id == id }) {
            println("Tarea $id eliminada.")
        } else {
            println("No se encontró la tarea con ID $id.")
        }
    }

    fun cambiarEstado(id: Int) {
        val tarea = listaTareas.find { it.id == id }
        if (tarea != null) {
            if (!tarea.realizada) {
                tarea.marcarComoRealizada()
                println("Tarea marcada como realizada.")
            } else {
                println("La tarea ya estaba realizada.")
            }
        } else {
            println("Tarea no encontrada.")
        }
    }

    fun mostrarTodas() = imprimirLista(listaTareas, "TODAS LAS TAREAS")
    fun mostrarPendientes() = imprimirLista(listaTareas.filter { !it.realizada }, "TAREAS PENDIENTES")
    fun mostrarRealizadas() = imprimirLista(listaTareas.filter { it.realizada }, "TAREAS REALIZADAS")

    private fun imprimirLista(lista: List<Tarea>, titulo: String) {
        println("\n--- $titulo ---")
        if (lista.isEmpty()) println("No hay tareas.")
        else lista.forEach { println(it) }
    }
}

fun main() {
    val gestor = GestorTareas()
    var opcion: Int

    do {
        println("\n--- MENÚ DE TAREAS ---")
        println("1. Agregar tarea")
        println("2. Eliminar tarea")
        println("3. Cambiar estado (Marcar realizada)")
        println("4. Mostrar todas")
        println("5. Mostrar pendientes")
        println("6. Mostrar realizadas")
        println("0. Salir")
        print("Selecciona una opción: ")

        opcion = readln().toIntOrNull() ?: -1

        when (opcion) {
            1 -> {
                print("Descripción de la tarea: ")
                gestor.agregarTarea(readln())
            }
            2 -> {
                print("ID de la tarea a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                gestor.eliminarTarea(id)
            }
            3 -> {
                print("ID de la tarea a completar: ")
                val id = readln().toIntOrNull() ?: 0
                gestor.cambiarEstado(id)
            }
            4 -> gestor.mostrarTodas()
            5 -> gestor.mostrarPendientes()
            6 -> gestor.mostrarRealizadas()
            0 -> println("Saliendo...")
            else -> println("Opción no válida.")
        }
    } while (opcion != 0)
}