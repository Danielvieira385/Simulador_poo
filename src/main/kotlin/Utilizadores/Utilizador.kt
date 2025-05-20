package com.example.Utilizadores

import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.attoparser.dom.DOMWriter.writeText
import java.io.File

@Serializable
data class Utilizador(
    var id: Int = 0,
    var nome: String = "",
    var idade : Int = 0,
    var password : String = ""
) {

    override fun toString(): String {
        return " ID do Personagem - ${id}" +
                "   Personagem ${nome}: " +
                "   Categoria Principal : ${idade}"
    }

    companion object {


        private val CAMINHODATA = "./src/main/resources/data"
        private val utilizadores = mutableMapOf<String, Utilizador>()

        fun obterID_Utilizador(): Int {
            val utilizadores_criados = obterTodosUtilizadores()
            return if (utilizadores_criados.isEmpty()) 1 else utilizadores_criados.maxOf { it.id } + 1
        }

        fun obterTodosUtilizadores(): List<Utilizador> {
            val file = File("${Utilizador.CAMINHODATA}/utilizadores.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }


        fun criarUtilizador(id: Int, nome: String, idade: Int, password: String) : Utilizador{
            val novoUtilizador = Utilizador(
                id = obterID_Utilizador(),
                nome = nome,
                idade = idade,
                password = password
            )

            val utilizadoresExistentes = obterTodosUtilizadores().toMutableList()
            utilizadoresExistentes.add(novoUtilizador)

            File("${CAMINHODATA}/utilizadores.json").apply {
                parentFile.mkdirs()
                writeText(json.encodeToString(utilizadoresExistentes))

            }
            return novoUtilizador
        }

    }
}
