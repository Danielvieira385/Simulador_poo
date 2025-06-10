package com.example.Menu

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Missao(
    var id: Int = 0,
    var nome: String = "",
    var exp: Int = 0,
    var recompensa_coins: Int = 0,
    var nivel: Int = 0,
    var adversario_id: Int = 0,
    var descricao: String = ""
) {
    companion object {
        val CAMINHODATA = "./src/main/resources/data"
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        fun obterIDMissao(): Int {
            val missoes = obterTodasMissoes()
            return if (missoes.isEmpty()) 1 else missoes.maxOf { it.id } + 1
        }

        fun obterTodasMissoes(): List<Missao> {
            val file = File("$CAMINHODATA/missoes.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }

        fun criarMissao(id: Int,nome: String,exp: Int, recompensa_coins: Int, nivel: Int, adversario_id: Int,descricao : String): Missao {
            val novaMissao = Missao(
                id = obterIDMissao(),
                nome = nome,
                exp = exp,
                recompensa_coins = recompensa_coins,
                nivel = nivel,
                adversario_id = adversario_id,
                descricao = descricao
            )

            val missoesExistentes = obterTodasMissoes().toMutableList()
            missoesExistentes.add(novaMissao)

            File("$CAMINHODATA/missoes.json").apply {
                parentFile.mkdirs()
                writeText(json.encodeToString(missoesExistentes))
            }

            println("Missao criada: $novaMissao")
            return novaMissao
        }
    }
}