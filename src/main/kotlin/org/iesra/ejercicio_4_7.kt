package org.iesra

class Cuenta(val numero: String, var saldo: Double = 0.0) {

    fun recibirAbono(cantidad: Double) { saldo += cantidad }

    fun realizarPago(cantidad: Double) { saldo -= cantidad }

    companion object {
        fun esMorosa(persona: Persona) = persona.cuentas.any { it != null && it.saldo < 0 }

        fun transferencia(personaOrigen: Persona, indiceOrigen: Int, personaDestino: Persona, indiceDestino: Int, cantidad: Double): Boolean {
            val origen = personaOrigen.cuentas.getOrNull(indiceOrigen)
            val destino = personaDestino.cuentas.getOrNull(indiceDestino)

            return if (origen != null && destino != null && origen.saldo >= cantidad) {
                origen.realizarPago(cantidad)
                destino.recibirAbono(cantidad)
                true
            } else false
        }
    }
}

class Persona(val dni: String) {
    val cuentas: Array<Cuenta?> = arrayOfNulls(3)

    fun añadirCuenta(nuevaCuenta: Cuenta): Boolean {
        val indiceLibre = cuentas.indexOfFirst { it == null }
        if (indiceLibre != -1) {
            cuentas[indiceLibre] = nuevaCuenta
            return true
        }
        return false
    }
}

fun main() {
    val persona = Persona("12345678Z")
    val cuenta1 = Cuenta("ES01", 0.0)
    val cuenta2 = Cuenta("ES02", 700.0)

    persona.añadirCuenta(cuenta1)
    persona.añadirCuenta(cuenta2)

    persona.cuentas[0]?.recibirAbono(1100.0)
    persona.cuentas[1]?.realizarPago(750.0)

    println("¿Es morosa la persona?: ${Cuenta.esMorosa(persona)}")

    if (Cuenta.transferencia(persona, 0, persona, 1, 100.0)) {
        println("Transferencia realizada con éxito.")
    }

    println("Estado final - ¿Es morosa?: ${Cuenta.esMorosa(persona)}")
    persona.cuentas.filterNotNull().forEach { println("Cuenta: ${it.numero}, Saldo: ${it.saldo}€") }
}