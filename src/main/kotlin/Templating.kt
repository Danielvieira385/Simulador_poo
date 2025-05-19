package com.example

import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion.criarPersonagem
import com.example.Criação_personagem.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

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
            val params = call.receiveParameters()
            val nome = params["nome_personagem"] ?: ""
            val categoriaPrincipal = params["categoria_principal"] ?: ""
            val categoriaSecundaria = params["categoria_secundaria"]?: ""
            val nivel = 0
            val inventario = listOf<Int>()

            val personagem = criarPersonagem(nome, categoriaPrincipal, categoriaSecundaria, nivel, inventario)


            // Aqui podes guardar numa base de dados, numa lista ou simplesmente responder
            call.respond(ThymeleafContent("vila", mapOf("personagem" to Personagem.todas())))
        }


    }
}
data class ThymeleafUser(val id: Int, val name: String)
