package com.example

import com.example.Adversários.Adversário.Companion.criarAdversario
import com.example.Adversários.Adversário.Companion.obterID_Adversario
import com.example.Adversários.Adversário.Companion.obterTodosAdversarios
import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion
import com.example.Criacao_personagem.Personagem.Companion.criarPersonagem
import com.example.Criacao_personagem.Personagem.Companion.obterID_Personagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
import com.example.Criacao_personagem.Personagem.Companion.personagens
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
                if (utilizador.nome == "Conta Administrador" && utilizador.password == "012") {
                    call.respondRedirect("/ferramentasAdmin")
                } else {
                    call.respondRedirect("/menu?id=${utilizador.id}")
                }
            } else {
                call.respond(ThymeleafContent("usersMenu", mapOf("erro" to "Credenciais inválidas")))
            }
        }
        get("/ferramentasAdmin") {
            call.respond(ThymeleafContent("ferramentasAdmin", mapOf("titulo" to "Painel do Administrador")))
        }
        get("/paginaAdminAdversarios") {
            call.respond(ThymeleafContent("paginaAdminAdversarios",mapOf()))
        }
        post("/paginaAdminAdversarios") {
            var params = call.receiveParameters()
            val id = obterID_Adversario()
            val nome = params["nome_adversario"]?:""
            val categoriaPrincipal = params["categoria_principal"]?:""
            val categoriaSecundaria = params["categoria_secundaria"]?:""
            val nivel = params["nivelAdversario"]?:""
            val arma = params["armaAdversario"]?:""

            val adversario = criarAdversario(id,nome,categoriaPrincipal,categoriaSecundaria,nivel.toInt(),arma.toInt())
            call.respond(ThymeleafContent("ferramentasAdmin",mapOf()))
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
            call.respond(ThymeleafContent("usersMenu",mapOf("utilizador" to utilizador)))
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
        post("/criar_personagem") {
            val params = call.receiveParameters()
            val idUtilizador = params["id_Utilizador"]
            if (idUtilizador != null) {
                call.respond(ThymeleafContent("criar_personagem", mapOf("idUtilizador" to idUtilizador)))
            } else {
                call.respondRedirect("/menu")
            }
        }
        get("/criar_personagem") {
            val idUtilizador = call.request.queryParameters["id_Utilizador"]
            if (idUtilizador != null) {
                call.respond(ThymeleafContent("criar_personagem", mapOf("idUtilizador" to idUtilizador)))
            } else {
                call.respondRedirect("/menu")
            }
        }
        get("/consultaBaseDados") {
            call.respond(ThymeleafContent("consultaBaseDados", mapOf("title" to ThymeleafUser(1, "user1"))))
        }
        get("/selecionarPersonagem") {
            val idUtilizador = call.request.queryParameters["id_Utilizador"]
            if (idUtilizador != null) {
                call.respond(ThymeleafContent("selecionarPersonagem", mapOf("idUtilizador" to idUtilizador)))
            } else {
                call.respondRedirect("/menu")
            }
        }
        post("/selecionarPersonagem") {
            val params = call.receiveParameters()
            val idUtilizador = params["id_Utilizador_select"]?.toIntOrNull()
            val personagens = obterTodosPersonagens()
            val personagensUtilizador = personagens.filter {
                it.idUtilizador == (idUtilizador?.toInt() ?: "")
            }
            if (idUtilizador != null) {
                call.respond(ThymeleafContent("selecionarPersonagem", mapOf("personagensUtilizador" to personagensUtilizador)))
            } else {
                call.respondRedirect("/menu")
            }
        }

        post("/vila") {
            val params = call.receiveParameters()
            val tipoBusca = params["tipoBusca"] ?: ""
            val idUtilizador = params["id_Utilizador"] ?:""
            if (tipoBusca == "criacao") {

                val id = obterID_Personagem()
                val nome = params["nome_personagem"] ?: ""
                val categoriaPrincipal = params["categoria_principal"] ?: ""
                val categoriaSecundaria = params["categoria_secundaria"]?: ""
                val nivel = 1
                val inventario = listOf<Int>(1)
                val coins = 1
                val progresso = 1
                val personagem = criarPersonagem(id,idUtilizador.toInt(), nome, categoriaPrincipal, categoriaSecundaria, nivel, inventario,coins,progresso)
                call.respond(ThymeleafContent("vila", mapOf("personagem" to personagem)))

            } else if (tipoBusca == "selecao") {
                val personagemSelecionado = params["personagemSelecionado"]?:""
                println(personagemSelecionado)
                val todasPersonagens = obterTodosPersonagens()
                val personagem = todasPersonagens.find { it.id == personagemSelecionado.toInt() }
                if (personagem != null) {
                    call.respond(ThymeleafContent("vila", mapOf("personagem" to personagem)))
                } else {
                    call.respond(ThymeleafContent("menu", mapOf("erro" to "Personagem não encontrado")))
                }
            } else {
                call.respond(ThymeleafContent("menu", mapOf("idUtilizador" to idUtilizador)))
            }
            }

        post("/arena") {
            val params = call.receiveParameters()
            val personagemId = params["personagemId"]?.toIntOrNull()
            val personagem = obterTodosPersonagens().find { it.id == personagemId }
            val adversarios = obterTodosAdversarios()
            var proximoAdversario = adversarios.find { it.id == personagem?.progresso }

            if (personagem != null) {
                val context = mutableMapOf<String, Any>(
                    "adversarios" to adversarios,
                    "personagem" to personagem
                )
                proximoAdversario?.let {
                    context["proximoAdversario"] = it
                }

                call.respond(ThymeleafContent("arena", context))
            }
        }
        }

        }
data class ThymeleafUser(val id: Int, val name: String)
