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


import com.example.Utilizadores.Utilizador.*
import com.example.Utilizadores.Utilizador.Companion.criarUtilizador
import com.example.Utilizadores.Utilizador.Companion.obterID_Utilizador
import com.example.Utilizadores.Utilizador.Companion.obterTodosUtilizadores
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
        get("/usersMenu") {
            call.respond(ThymeleafContent("usersMenu", mapOf()))
        }
        post("/usersMenu") {
            val params = call.receiveParameters()
            val nomeUtilizador = params["nome_utilizador"] ?: ""
            val password = params["password"] ?: ""

            val utilizadores = obterTodosUtilizadores()
            val utilizador = utilizadores.find { it.nome == nomeUtilizador && it.password == password }
            if (utilizador != null) {
                call.respondRedirect("/menu?id=${utilizador.id}")
            } else {
                call.respond(ThymeleafContent("usersMenu", mapOf("erro" to "Credenciais inválidas")))
            }
        }
        get("/menu") {
            val idUtilizador = call.request.queryParameters["id"]?.toIntOrNull()
            val utilizador = obterTodosUtilizadores().find { it.id == idUtilizador }
            if (utilizador != null) {
                call.respond(ThymeleafContent("menu", mapOf("utilizador" to utilizador)))
            } else {
                call.respondRedirect("/usersMenu")
            }
        }
        get("/criarUtilizador") {
            call.respond(ThymeleafContent("criarUtilizador",mapOf()))
        }
        post("/criarUtilizador") {
            var params = call.receiveParameters()
            val id = obterID_Utilizador()
            val nome = params["nomeUtilizador"]?:""
            val password = params["password"]?:""
            val idade = params["idade"]?:""

            val utilizador = criarUtilizador(id,nome,idade.toInt(),password)
            print(utilizador.toString())
            call.respond(ThymeleafContent("usersMenu",mapOf()))
        }

        get("/criar_personagem") {
            val idUtilizador = call.request.queryParameters["id"]?.toIntOrNull()
            val utilizador = obterTodosUtilizadores().find { it.id == idUtilizador }

            if (utilizador != null) {
                call.respond(ThymeleafContent("criar_personagem", mapOf("utilizador" to utilizador)))
            }else {
                call.respondRedirect("/usersMenu")
            }
        }
        get("/consultaBaseDados") {
            call.respond(ThymeleafContent("consultaBaseDados", mapOf("title" to ThymeleafUser(1, "user1"))))
        }
        post("/vila") {
            val params = call.receiveParameters()
            val id = obterID_Personagem()
            val idUtilizador = params["id_Utilizador"] ?:""
            val nome = params["nome_personagem"] ?: ""
            val categoriaPrincipal = params["categoria_principal"] ?: ""
            val categoriaSecundaria = params["categoria_secundaria"]?: ""
            val nivel = 1
            val inventario = listOf<Int>(1)
            val coins = 1

            val personagem = criarPersonagem(id,idUtilizador.toInt(), nome, categoriaPrincipal, categoriaSecundaria, nivel, inventario,coins)
            //val personagem_criada = Personagem(id,nome,categoriaPrincipal,categoriaSecundaria,nivel,inventario,coins)

            val CAMINHODATA = "./src/main/resources/data"

            call.respond(ThymeleafContent("vila", mapOf("personagem" to personagem)))
            }

        }


    }

data class ThymeleafUser(val id: Int, val name: String)
