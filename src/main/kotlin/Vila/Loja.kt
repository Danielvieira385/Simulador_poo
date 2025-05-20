package com.example.Vila
import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
class Loja(
    val id: Int,
    val nome: String,
    val dano: Int,
    val tipo: String,
    val nivel: Int
) {
    override fun toString(): String {
        return "\nID = $id, " +
                "Nome = $nome, " +
                "Dano = $dano, " +
                "Tipo de arma = $tipo, " +
                "Nível da arma = $nivel"
    }

    companion object {
        val CAMINHODATA = "./src/main/resources/data"

        fun mostrarTodosObjetos(): List<Loja> {
            val file = File("$CAMINHODATA/armazemLoja.json")
            if (!file.exists() || file.readText().isEmpty()) return emptyList()
            return json.decodeFromString(file.readText())
        }

        fun comprarObjeto() {
            val objetos = mostrarTodosObjetos()
            for (objeto in objetos) {
                println("ID: ${objeto.id} Nome: ${objeto.nome}")
            }
            val objetoEscolhido = readLine().toString()

        }

        fun venderObjeto() {

        }
    }
}
