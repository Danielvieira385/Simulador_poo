package com.example

import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion
import com.example.Criacao_personagem.Personagem.Companion.criarPersonagem
import com.example.Criacao_personagem.Personagem.Companion.obterID_Personagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
import com.example.Menu.Arma
import com.example.Menu.Arma.Companion.json
import com.example.Vila.Vila
import com.example.Vila.Loja


import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import kotlinx.serialization.json.Json
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.File

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
    routing {
        get("/") {
            call.respond(ThymeleafContent("iniciar", mapOf("user" to ThymeleafUser(1, "user1"))))
        }
        get("/iniciar") {
            call.respond(ThymeleafContent("iniciar", mapOf("title" to ThymeleafUser(1, "user1"))))
        }
        get("/menu") {
            call.respond(ThymeleafContent("menu", mapOf("title" to ThymeleafUser(1, "user1"))))
        }
        get("/criar_personagem") {
            call.respond(ThymeleafContent("criar_personagem", mapOf("title" to ThymeleafUser(1, "user1"))))
        }
        get("/consultaBaseDados") {
            call.respond(ThymeleafContent("consultaBaseDados", mapOf("title" to ThymeleafUser(1, "user1"))))
        }
        post("/vila") {
            var params = call.receiveParameters()
            val id = obterID_Personagem()
            val nome = params["nome_personagem"] ?: ""
            val categoriaPrincipal = params["categoria_principal"] ?: ""
            val categoriaSecundaria = params["categoria_secundaria"]?: ""
            val nivel = 1
            val inventario = listOf<Int>(1)
            val coins = 1

            val personagem = criarPersonagem(id, nome, categoriaPrincipal, categoriaSecundaria, nivel, inventario,coins)
            //val personagem_criada = Personagem(id,nome,categoriaPrincipal,categoriaSecundaria,nivel,inventario,coins)

            //val CAMINHODATA = "./src/main/resources/data"

            call.respond(ThymeleafContent("vila", mapOf("personagem" to personagem)))
            }

        }


    }

data class ThymeleafUser(val id: Int, val name: String)
