package com.example.Utilizadores

import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.attoparser.dom.DOMWriter.writeText
import java.io.File

@Serializable
data class Administrador(
    var id: Int = 0,
    var nome: String = "",
    var password : String = ""
) {

    override fun toString(): String {
        return " ID do Utilizador - ${id}" +
                "   Nome do Utilizador : ${nome} "
    }

    companion object {


        private val CAMINHODATA = "./src/main/resources/data"
        private val administradores = mutableMapOf<String, Administrador>()

        fun obterID_Administrador(): Int {
            val administradores_criados = obterAdministradores()
            return if (administradores_criados.isEmpty()) 1 else administradores_criados.maxOf { it.id } + 1
        }

        fun obterAdministradores(): List<Administrador> {
            val file = File("${Utilizador.CAMINHODATA}/administradores.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }


        fun criarAdministrador(id: Int, nome: String, password: String, codigo: Int) : Administrador? {
            val file = File("${Utilizador.CAMINHODATA}/codigosAdmin.json")
            val jsonString = file.readText()
            val codigoFicheiro = Regex("\\d+").findAll(jsonString).joinToString("") { it.value }
            if (codigoFicheiro == codigo.toString()) {
                val novoAdministrador = Administrador(
                    id = obterID_Administrador(),
                    nome = nome,
                    password = password
                )

                val administradoresExistentes = obterAdministradores().toMutableList()
                administradoresExistentes.add(novoAdministrador)

                File("${CAMINHODATA}/administradores.json").apply {
                    parentFile.mkdirs()
                    writeText(json.encodeToString(administradoresExistentes))

                }
                return novoAdministrador
            }
            return null
        }
    }
}
