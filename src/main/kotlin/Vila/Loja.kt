package com.example.Vila
import Criação_personagem.InventarioData
import Criação_personagem.Item
import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion.atualizarPersonagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
import com.example.Menu.Arma
import com.example.Menu.Arma.Companion
import com.example.Menu.Arma.Companion.obterID
import com.example.Menu.Arma.Companion.obterTodasArmas


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
        return "\nNome = $nome, " +
                "Dano = $dano, " +
                "Preço = $preco"
    }

    companion object {
        val CAMINHODATA = "./src/main/resources/data"

        fun obterIDLoja(): Int {
            val armas = obterTodasArmasLoja()
            return if (armas.isEmpty()) 1 else armas.maxOf { it.id } + 1
        }

        fun obterTodasArmasLoja(): List<Loja> {
            val file = File("${Loja.CAMINHODATA}/armazemLoja.json")
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                return emptyList()
            }
            val jsonString = file.readText()
            return if (jsonString.isEmpty()) emptyList()
            else json.decodeFromString(jsonString)
        }

        fun adicionarArmaLoja (nome: String, dano: Int, tipo: String, nivel: Int, preco: Int) {
            val novaEntradaLoja = Loja (
                id = obterIDLoja(),
                nome = nome,
                dano = dano,
                tipo = tipo,
                nivel = nivel,
                preco = preco
            )

            val entradasLoja = obterTodasArmasLoja().toMutableList()
            entradasLoja.add(novaEntradaLoja)

            File("${com.example.Menu.Arma.CAMINHODATA}/armazemLoja.json").apply {
                parentFile.mkdirs()
                writeText(json.encodeToString(entradasLoja))
            }

            println("Arma inserida na Loja: $novaEntradaLoja")
        }

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
                    val personagem = obterTodosPersonagens().find {it.id == idPersonagem}

                    if (personagem != null) {
                        if (personagem.coins >= arma.preco && personagem.nivel >= arma.nivel) {
                            inventario.adicionarArmaInventario(idArma)
                            personagem.coins -= arma.preco
                            atualizarPersonagem("coins",personagem.coins.toString(),personagem)
                            println("Você comprou a arma: ${arma.nome}")
                        }
                    } else {
                        println("Não pode comprar a arma: ${arma.nome}")
                    }
                }
            }
        }

        fun venderObjeto(idArma: Int, idPersonagem: Int) {
            val armazem: List<Loja> = mostrarTodasArmas()
            val inventario: Item = Item(idPersonagem)

            for (arma in armazem) {
                if (arma.id.toInt() == idArma) {
                    val personagem = obterTodosPersonagens().find {it.id == idPersonagem}
                    if (personagem != null) {
                        println(arma.preco)
                        inventario.removerArmaInventario(idArma)
                        personagem.coins += arma.preco
                        atualizarPersonagem("coins",personagem.coins.toString(),personagem)
                        println("Você vendeu a arma: ${arma.nome}")
                    }
                }
            }
        }
    }
}
