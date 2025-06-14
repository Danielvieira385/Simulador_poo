package com.example.Adversários

import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class Adversário(
    var id: Int = 0,
    var nome: String = "",
    var categoriaPrincipal: String = "",
    var categoriaSecundaria: String = "",
    var nivel: Int = 0,
    var arma: Int = 0
) {

    override fun toString(): String {
        return " ID do Adversario - ${id}" +
                "   Adversario ${nome}: " +
                "   Categoria Principal : ${categoriaPrincipal}" +
                "   Categoria Secundaria : ${categoriaSecundaria}" +
                "   Nível: $nivel" +
                "   Arma: $arma"
    }

    companion object {


        private val CAMINHODATA = "./src/main/resources/data"
        private val adversarios = mutableMapOf<String, Adversário>()

        fun obterID_Adversario(): Int {
            val adversarios_criados= obterTodosAdversarios()
            return if (adversarios_criados.isEmpty()) 1 else adversarios_criados.maxOf { it.id } + 1
        }

        fun obterTodosAdversarios(): List<Adversário> {
            val file = File("${Adversário.CAMINHODATA}/adversarios.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }


        fun criarAdversario(
            id: Int, nome: String, categoriaPrincipal: String, categoriaSecundaria: String,
            nivel: Int, arma: Int
        ) : Adversário{
            val novoAdversario = Adversário(
                id = obterID_Adversario(),
                nome = nome,
                categoriaPrincipal = categoriaPrincipal,
                categoriaSecundaria = categoriaSecundaria,
                nivel = nivel,
                arma = arma,
            )

            val adversariosExistentes = obterTodosAdversarios().toMutableList()
            adversariosExistentes.add(novoAdversario)

            File("${CAMINHODATA}/adversarios.json").apply {
                parentFile.mkdirs()
                writeText(json.encodeToString(adversariosExistentes))

            }

            return novoAdversario
        }

    }
}
