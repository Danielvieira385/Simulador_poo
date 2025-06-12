package Criação_personagem

import com.example.Menu.Arma
import com.example.Menu.Arma.Companion.obterTodasArmas
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import java.io.File

@Serializable
data class InventarioData(val inventarios: MutableMap<Int, MutableList<Int>> = mutableMapOf())

class Item(val idPersonagem: Int, val idArmas: MutableList<Int> = mutableListOf()) {
    val CAMINHODATA = "./src/main/resources/data"
    private val json = Json { ignoreUnknownKeys = true }
    val file = File("$CAMINHODATA/inventario.json")

    init {
        if (file.exists() && file.readText().isNotEmpty()) {
            val inventarioData: InventarioData = json.decodeFromString(file.readText())
            idArmas.addAll(inventarioData.inventarios.getOrDefault(idPersonagem, mutableListOf()))
        } else {
            // Se o arquivo não existir, inicializa o inventário vazio
            idArmas.clear()
        }
    }

    override fun toString(): String {
       return "Inventário = $idArmas"
    }

    // Adiciona uma arma ao inventário do personagem
    fun adicionarArmaInventario(idArma: Int) {
        if (!idArmas.contains(idArma)) {
            idArmas.add(idArma)

            val inventarioData: InventarioData =
                if (file.exists() && file.readText().isNotEmpty())
                    json.decodeFromString(file.readText())
                else
                    InventarioData()
            inventarioData.inventarios[idPersonagem] = idArmas
            file.writeText(json.encodeToString(inventarioData))
        }
    }

    fun removerArmaInventario(idArma: Int) {
        if (idArmas.remove(idArma)) {
            val file = File("$CAMINHODATA/inventario.json")
            val inventarioData: InventarioData =
                if (file.exists() && file.readText().isNotEmpty())
                    json.decodeFromString(file.readText())
                else
                    InventarioData()
            inventarioData.inventarios[idPersonagem] = idArmas
            file.writeText(json.encodeToString(inventarioData))
        }
    }

    fun mostrarArmasInventarioDeTodosID(): List<Arma> {
        val fileArmas = File("$CAMINHODATA/armas.json")
        if (!fileArmas.exists() || fileArmas.readText().isEmpty()) return emptyList()
        val todasArmas: List<Arma> = json.decodeFromString(fileArmas.readText())
        return todasArmas.filter { it.id in idArmas }
    }

    fun mostrarArmasInventarioPorID(idPersonagem: Int): List<Int> {
        if (!file.exists() || file.readText().isEmpty()) return emptyList()
        val inventarioData: InventarioData = json.decodeFromString(file.readText())
        return inventarioData.inventarios[idPersonagem] ?: emptyList()
    }

    fun mostarArmasInventarioNome(inventario: List<Int>): List<String> {
        val todasArmas = obterTodasArmas()
        val nomesArmas = mutableListOf<String>()
        for (item in inventario) {
            for (arma in todasArmas) {
                if (item == arma.id) {
                    nomesArmas.add(arma.nome)
                }
            }
        }
        return nomesArmas
    }

}
