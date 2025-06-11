package com.example.Menu

import com.example.Adversários.Adversário.Companion.obterTodosAdversarios
import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
import com.example.Criacao_personagem.Personagem.Companion.passarNivel
import com.example.Vila.Combate
import com.example.Vila.Combate.*
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
    var descricao: String = "",
    var duracao: Int = 0
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

        fun criarMissao(id: Int,nome: String,exp: Int, recompensa_coins: Int, nivel: Int, adversario_id: Int,descricao : String, duracao : Int): Missao {
            val novaMissao = Missao(
                id = obterIDMissao(),
                nome = nome,
                exp = exp,
                recompensa_coins = recompensa_coins,
                nivel = nivel,
                adversario_id = adversario_id,
                descricao = descricao,
                duracao = duracao
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

        fun começarMissao(missao: Missao, personagem: Personagem): Triple<String, Int, String>? {
            val adversario = obterTodosAdversarios().find { it.id == missao.adversario_id } ?: return null

            val combate = Combate(personagem, adversario)
            val batalha = combate.comecarBatalha()
            var duracaoMissao = missao.duracao
            var expMissao = passarNivel(missao.exp, personagem)

            return Triple(batalha, duracaoMissao, expMissao)
        }

    }
}