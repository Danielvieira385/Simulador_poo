package com.example.Menu

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Arma(
    var id: Int = 0,
    var nome: String = "",
    var dano: Int = 0,
    var tipo: String = "",
    var nivel: Int = 0
) {
    companion object {
        val CAMINHODATA = "./src/main/resources/data"
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        fun obterID(): Int {
            val armas = obterTodasArmas()
            return if (armas.isEmpty()) 1 else armas.maxOf { it.id } + 1
        }

        fun obterTodasArmas(): List<Arma> {
            val file = File("$CAMINHODATA/armas.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }

        fun criarArma(nome: String, dano: Int, tipo: String, nivel: Int): Arma {
            val novaArma = Arma(
                id = obterID(),
                nome = nome,
                dano = dano,
                tipo = tipo,
                nivel = nivel
            )

            val armasExistentes = obterTodasArmas().toMutableList()
            armasExistentes.add(novaArma)

            File("$CAMINHODATA/armas.json").apply {
                parentFile.mkdirs()
                writeText(json.encodeToString(armasExistentes))
            }

            println("Arma criada: $novaArma")
            return novaArma
        }
    }
}