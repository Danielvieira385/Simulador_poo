package com.example.Vila
import Criação_personagem.InventarioData
import Criação_personagem.Item
import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.example.Criacao_personagem.Personagem


@Serializable
class Loja(
    val id: Int,
    val nome: String,
    val dano: Int,
    val tipo: String,
    val nivel: Int,
    val preco: Int
) {
    override fun toString(): String {
        return "\nID = $id, " +
                "Nome = $nome, " +
                "Dano = $dano, " +
                "Tipo de arma = $tipo, " +
                "Nível da arma = $nivel" +
                "Preço = $preco"
    }

    companion object {
        val CAMINHODATA = "./src/main/resources/data"

        fun mostrarTodasArmas(): List<Loja> {
            val file = File("$CAMINHODATA/armazemLoja.json")
            if (!file.exists() || file.readText().isEmpty()) return emptyList()
            return json.decodeFromString(file.readText())
        }

        fun comprarObjeto(idArma: Int, idPersonagem: Int) {
            val armazem: List<Loja> = mostrarTodasArmas()
            val inventario: Item = Item(idPersonagem)

            for (arma in armazem) {
                if (arma.id == idArma) {
                    val personagem = Personagem(idPersonagem)

                    if (personagem.coins >= arma.preco) {
                        personagem.coins -= arma.preco
                        inventario.adicionarArmaInventario(idArma)

                        // Falta Remove a arma do armazém após a compra

                        println("Você comprou a arma: ${arma.nome}")

                    } else {
                        println("Dinheiro insuficiente para comprar a arma: ${arma.nome}")
                    }
                }
            }
        }

        fun venderObjeto(idArma: Int, idPersonagem: Int) {
            val inventario: Item = Item(idPersonagem)
            val armazem = mostrarTodasArmas()

            for (arma in armazem) {
                if (arma.id == idArma) {
                    if (inventario.idArmas.contains(idArma)) {
                        inventario.removerArmaInventario(idArma)
                        val personagem = Personagem(idPersonagem)
                        personagem.coins += arma.preco / 2 // vender por metade do preço
                        println("Você vendeu a arma: ${arma.nome}")
                    } else {
                        println("Você não possui a arma: ${arma.nome} para vender.")
                    }
                }
            }



        }
    }
}
