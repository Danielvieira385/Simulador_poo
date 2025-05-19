package com.example.Criação_personagem

import kotlinx.serialization.Serializable


class Personagem {
    var nome: String = ""
    var categoriaPrincipal: String = ""
    var categoriaSecundaria: String = ""
    var nivel : Int = 0

    fun criarPersonagem(nome: String, categoriaPrincipal: String, categoriaSecundaria: String, nivel : Int) {
        this.nome = nome
        this.categoriaPrincipal = categoriaPrincipal
        this.categoriaSecundaria = categoriaSecundaria
        this.nivel = nivel
    }
}




