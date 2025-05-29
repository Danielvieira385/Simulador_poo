package Criação_personagem

import com.example.Menu.Arma
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import java.io.File

@Serializable
data class InventarioData(val inventarios: MutableMap<Int, MutableList<Int>> = mutableMapOf())

class Inventario(val idPersonagem: Int, val idArmas: MutableList<Int> = mutableListOf()) {
    val CAMINHODATA = "./src/main/resources/data"
    private val json = Json { ignoreUnknownKeys = true }

    fun adicionarArmaInventario(idArma: Int) {
        if (!idArmas.contains(idArma)) {
            idArmas.add(idArma)

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
// Ainda não está a funcionar corretamente, mas é para mostrar as armas de um personagem específico
//    fun mostrarArmasInventarioPorID(idPersonagem: Int): List<Int> {
//        val file = File("$CAMINHODATA/inventario.json")
//        if (!file.exists() || file.readText().isEmpty()) return emptyList()
//        val inventarioData: InventarioData = json.decodeFromString(file.readText())
//        return inventarioData.inventarios[idPersonagem] ?: emptyList()
//    }
}
