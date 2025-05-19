package com.example.Menu

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

class Arma(nome: String, dano: Int, tipo: String, nivel: Int, id: Int){
    var id: Int = 0
    var nome: String = ""
    var dano: Int = 0
    var tipo: String = ""
    var nivel: Int = 0

    val CAMINHODATA = "./src/main/resources/data"

    fun obterID(): Int {
        val arma = obterTodasArmas()
        return if (arma.isEmpty()) 1 else arma.maxOf { it.id } + 1
    }

    fun obterTodasArmas(): List<Arma> {
        val json = File("$CAMINHODATA/armas.json").readText()
        return Json.decodeFromString<List<Arma>>(json)
    }

    fun criarArma(nome: String, dano: Int, tipo: String, nivel: Int) {
        this.id = obterID()
        this.nome = nome
        this.dano = dano
        this.tipo = tipo
        this.nivel = nivel

        val listaArmas = listOf(
            Arma(nome, dano, tipo, nivel, id)
        )

        val novaArma = Arma(nome, dano, tipo, nivel, id)
        val guardarArma = Json.encodeToString(listaArmas.map { GuardarArma.construir(it) })
        File("$CAMINHODATA/armas.json").writeText(Json.encodeToString(guardarArma))
        println("Arma criada: $novaArma")
    }
}

@Serializable
data class GuardarArma(
    val id: Int,
    val nome: String,
    val dano: Int,
    val tipo: String,
    val nivel: Int){

    companion object {
        fun construir(arma: Arma): GuardarArma =
            GuardarArma(
                arma.id,
                arma.nome,
                arma.dano,
                arma.tipo,
                arma.nivel
            )
    }
}

