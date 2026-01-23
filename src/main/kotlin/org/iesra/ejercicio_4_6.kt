package org.iesra

data class Domicilio(val calle: String, val numero: Int) {
    fun dirCompleta(): String = "$calle $numero"
}

data class Cliente(val nombre: String, val domicilio: Domicilio)

data class Compra(val cliente: Cliente, val dia: Int, val monto: Double)

class RepositorioCompras {
    private val compras = mutableListOf<Compra>()

    fun agregarCompra(compra: Compra) {
        compras.add(compra)
    }

    fun domiciliosParaFacturas(): Set<String> {
        val direccionesUnicas = mutableSetOf<String>()
        for (compra in compras) {
            direccionesUnicas.add(compra.cliente.domicilio.dirCompleta())
        }
        return direccionesUnicas
    }
}

fun main() {
    val repo = RepositorioCompras()

    val dom1 = Domicilio("Plaza la barrosa", 79)
    val dom2 = Domicilio("Avenida Sanidad Publica", 19)

    val cliente1 = Cliente("Jesús", dom1)
    val cliente2 = Cliente("Ana", dom2)

    repo.agregarCompra(Compra(cliente1, 5, 150.0))
    repo.agregarCompra(Compra(cliente2, 6, 200.0))
    repo.agregarCompra(Compra(cliente1, 7, 50.0))

    repo.domiciliosParaFacturas().forEach { println(it) }
}
