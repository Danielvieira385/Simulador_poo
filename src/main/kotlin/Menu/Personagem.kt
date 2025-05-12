package com.example.Menu

class Personagem {
    var nome: String = ""
    var idade: Int = 0
    val classeSocial = CategoriaPrincipal().categoriaPrincipal
    val CategoriaSecundaria = CategoriaSecundaria().categoriaSecundaria

    fun criarPersonagem(nome: String, idade: Int, classeSocial: String, categoriaSecundaria: String) {
        this.nome = nome
        this.idade = idade
        this.classeSocial.add(classeSocial)
        this.CategoriaSecundaria.add(categoriaSecundaria)
    }

}