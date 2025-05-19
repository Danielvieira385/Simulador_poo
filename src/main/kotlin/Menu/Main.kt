package com.example.Menu

import com.example.Criacao_personagem.Personagem
import kotlinx.serialization.json.Json
import java.io.File

fun main() {

    val CAMINHODATA = "./src/main/resources/data"

    val Personagem1 = Personagem("Personagem1",
        "Guerreiro","Ferreiro",0, listOf<Int>())
    val Personagem2 = Personagem("Personagem2",
        "Guerreiro","Ferreiro",0, listOf<Int>(1,2,3))
    val Personagem3 = Personagem("Personagem3",
        "Guerreiro","Ferreiro",0, listOf<Int>(3,4,1))

    println(Personagem1)

    // Serializando em disco
    val jsonPersonagens = Json.encodeToString(Personagem.todas())
    File("$CAMINHODATA/personagens.json").writeText(jsonPersonagens)
    println("JSON salvo:\n$jsonPersonagens")

}