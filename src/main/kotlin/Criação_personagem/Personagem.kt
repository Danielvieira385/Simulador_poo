package com.example.Criacao_personagem

import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.attoparser.dom.DOMWriter.writeText
import java.io.File

@Serializable
data class Personagem(
    var id: Int = 0,
    var idUtilizador: Int = 0,
    var nome: String = "",
    var categoriaPrincipal: String = "",
    var categoriaSecundaria: String = "",
    var nivel: Int = 0,
    var inventario: List<Int> = listOf(),
    var coins : Int = 0,
    var progresso : Int = 1
) {

    override fun toString(): String {
        return " ID do Personagem - ${id}" +
                " ID do Utilizador - ${idUtilizador}" +
                "   Personagem: ${nome} " +
                "   Categoria Principal : ${categoriaPrincipal}" +
                "   Categoria Secundaria : ${categoriaSecundaria}" +
                "   Nível: $nivel" +
                "   Inventário: $inventario" +
                "   Dinheiro: $coins" +
                "   Progresso: $progresso"
    }

    companion object {


        private val CAMINHODATA = "./src/main/resources/data"
        val personagens = mutableMapOf<String, Personagem>()

        fun obterID_Personagem(): Int {
            val personagens_criadas = obterTodosPersonagens()
            return if (personagens_criadas.isEmpty()) 1 else personagens_criadas.maxOf { it.id } + 1
        }

        fun obterTodosPersonagens(): List<Personagem> {
            val file = File("${Personagem.CAMINHODATA}/personagens.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }

        var exp : Int = 0
        fun passarNivel(xp : Int, personagemAtiva : Personagem) :String {
            exp += xp
            if (exp >= 100) {
                personagemAtiva.nivel += 1
                exp = 0
                return "O ${personagemAtiva.nome} passou de nível!"
            } else {
                return "O ${personagemAtiva.nome} ganhou $xp"
            }
        }


        fun criarPersonagem(
            id: Int, idUtilizador: Int, nome: String, categoriaPrincipal: String, categoriaSecundaria: String,
            nivel: Int, inventario: List<Int>, coins: Int, progresso: Int
        ) : Personagem{
            val novaPersonagem = Personagem(
                id = obterID_Personagem(),
                idUtilizador = idUtilizador,
                nome = nome,
                categoriaPrincipal = categoriaPrincipal,
                categoriaSecundaria = categoriaSecundaria,
                nivel = nivel,
                inventario = inventario,
                coins = coins,
                progresso = progresso
            )

            val personagemExistentes = obterTodosPersonagens().toMutableList()
            personagemExistentes.add(novaPersonagem)

            println(novaPersonagem)
            File("${CAMINHODATA}/personagens.json").apply {
                parentFile.mkdirs()
                writeText(json.encodeToString(personagemExistentes))

            }

            return novaPersonagem
        }

    }
}
