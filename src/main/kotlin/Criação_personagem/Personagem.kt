package com.example.Criacao_personagem

import kotlinx.serialization.Serializable

@Serializable
data class Personagem(
    var nome: String = "",
    var categoriaPrincipal: String = "",
    var categoriaSecundaria: String = "",
    var nivel: Int = 0,
    var inventario: List<Int> = listOf()
) {
    override fun toString(): String {
        return "Personagem ${nome}: " +
                "   Categoria Principal : ${categoriaPrincipal}" +
                "   Categoria Secundaria : ${categoriaSecundaria}" +
                "   Nível: $nivel" +
                "   Inventário: $inventario"
    }
    companion object {

        private val personagens = mutableMapOf<String, Personagem>()

        fun criarPersonagem(
            nome: String,
            categoriaPrincipal: String,
            categoriaSecundaria: String,
            nivel: Int,
            inventario: List<Int>
        ): Personagem {
            return Personagem(
                nome,
                categoriaPrincipal,
                categoriaSecundaria,
                nivel,
                inventario
            )
        }
        fun todas(): List<Personagem> = personagens.values.toList()
    }
}
