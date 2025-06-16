package com.example

import Criação_personagem.Item
import com.example.Adversários.Adversário.Companion.criarAdversario
import com.example.Adversários.Adversário.Companion.obterID_Adversario
import com.example.Adversários.Adversário.Companion.obterTodosAdversarios
import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion
import com.example.Criacao_personagem.Personagem.Companion.atualizarPersonagem
import com.example.Criacao_personagem.Personagem.Companion.criarPersonagem
import com.example.Criacao_personagem.Personagem.Companion.obterID_Personagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
import com.example.Criacao_personagem.Personagem.Companion.passarNivel
import com.example.Criacao_personagem.Personagem.Companion.personagens
import com.example.Menu.Arma
import com.example.Menu.Arma.Companion.criarArma
import com.example.Menu.Arma.Companion.json
import com.example.Menu.Arma.Companion.obterTodasArmas
import com.example.Menu.Missao
import com.example.Menu.Missao.Companion.criarMissao
import com.example.Menu.Missao.Companion.obterIDMissao
import com.example.Menu.Missao.Companion.obterTodasMissoes
import com.example.Vila.Vila
import com.example.Vila.Loja
import com.example.Vila.Loja.Companion.mostrarTodasArmas



import com.example.Utilizadores.Utilizador.*
import com.example.Utilizadores.Utilizador.Companion.criarUtilizador
import com.example.Utilizadores.Utilizador.Companion.obterID_Utilizador
import com.example.Utilizadores.Utilizador.Companion.obterTodosUtilizadores
import com.example.Vila.Combate
import com.example.Vila.Loja.Companion.adicionarArmaLoja
import com.example.Vila.Loja.Companion.comprarObjeto
import com.example.Vila.Loja.Companion.obterTodasArmasLoja
import com.example.Vila.Loja.Companion.venderObjeto
import io.ktor.http.*
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
            call.respond(ThymeleafContent("iniciar", mapOf()))
        }
        get("/iniciar") {
            call.respond(ThymeleafContent("iniciar", mapOf()))
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
            call.respond(ThymeleafContent("paginaAdminAdversarios", mapOf()))
        }
        post("/paginaAdminAdversarios") {
            var params = call.receiveParameters()
            val id = obterID_Adversario()
            val nome = params["nome_adversario"] ?: ""
            val categoriaPrincipal = params["categoria_principal"] ?: ""
            val categoriaSecundaria = params["categoria_secundaria"] ?: ""
            val nivel = params["nivelAdversario"] ?: ""
            val arma = params["armaAdversario"] ?: ""

            val adversario =
                criarAdversario(id, nome, categoriaPrincipal, categoriaSecundaria, nivel.toInt(), arma.toInt())
            call.respond(ThymeleafContent("ferramentasAdmin", mapOf()))
        }
        get("/paginaAdminMissao") {
            call.respond(ThymeleafContent("paginaAdminMissao", mapOf()))
        }
        post("/paginaAdminMissao") {
            var params = call.receiveParameters()
            val id = obterIDMissao()
            val nome = params["nome_missao"] ?: ""
            val exp = params["exp_missao"] ?: ""
            val recompensa_coins = params["recompensa_coins"] ?: ""
            val nivelPersonagem = params["nivelPersonagem"] ?: ""
            val adversario = params["adversario_missao"] ?: ""
            val descricao = params["descricaoMissao"] ?: ""
            val duracao = params["duracaoMissao"] ?:""

            val missao =
                criarMissao(id, nome, exp.toInt(), recompensa_coins.toInt(), nivelPersonagem.toInt(),
                    adversario.toInt(),descricao,duracao.toInt())
            call.respond(ThymeleafContent("ferramentasAdmin", mapOf()))
        }

        get("/paginaAdminArma") {
            call.respond(ThymeleafContent("paginaAdminArma", mapOf()))
        }

        post("/paginaAdminArma") {
            var params = call.receiveParameters()
            val nome = params["nome_arma"] ?: ""
            val dano = params["dano_arma"] ?: ""
            val tipo_arma = params["tipo_arma"] ?: ""
            val nivel_necessario = params["nivel_necessario"] ?: ""
            val preco_arma = params["preco_arma"] ?: ""

            val arma =
                criarArma(nome, dano.toInt(), tipo_arma, nivel_necessario.toInt(),
                    preco_arma.toInt())
            call.respond(ThymeleafContent("ferramentasAdmin", mapOf()))
        }

        get("/paginaAdminLoja") {
            val armas = obterTodasArmas()
            if (armas != null) {
                call.respond(ThymeleafContent("paginaAdminLoja", mapOf("armas" to armas)))
            }
        }

        post("/adicionarArmaLoja") {
            var params = call.receiveParameters()
            val armaId = params["armaId"] ?:""
            val todasArmas = obterTodasArmas()
            for (arma in todasArmas) {
                if (arma.id == armaId.toInt()) {
                    adicionarArmaLoja(arma.nome,arma.dano,arma.tipo,arma.nivel,arma.preco)
                }
            }
            println(obterTodasArmasLoja())
            call.respond(ThymeleafContent("paginaAdminLoja", mapOf("armas" to todasArmas)))
        }

        get("/criarUtilizador") {
            call.respond(ThymeleafContent("criarUtilizador", mapOf()))
        }

        post("/criarUtilizador") {
            var params = call.receiveParameters()
            val id = obterID_Utilizador()
            val nome = params["nomeUtilizador"] ?: ""
            val password = params["password"] ?: ""
            val idade = params["idade"] ?: ""

            val utilizador = criarUtilizador(id, nome, idade.toInt(), password)
            call.respond(ThymeleafContent("usersMenu", mapOf("utilizador" to utilizador)))
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
        //get("/consultaBaseDados") {
            //call.respond(ThymeleafContent("consultaBaseDados", mapOf("title" to ThymeleafUser(1, "user1"))))
        //}

        get("/selecionarPersonagem") {
            val idUtilizador = call.request.queryParameters["id_Utilizador"]
            if (idUtilizador != null) {
                call.respond(ThymeleafContent("selecionarPersonagem", mapOf("idUtilizador" to idUtilizador)))
            } else {
                call.respondRedirect("/usersMenu")
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
                call.respond(
                    ThymeleafContent(
                        "selecionarPersonagem",
                        mapOf("personagensUtilizador" to personagensUtilizador)
                    )
                )
            } else {
                call.respondRedirect("/usersMenu")
            }
        }

        post("/vila") {
            val params = call.receiveParameters()
            val tipoBusca = params["tipoBusca"] ?: ""
            val idUtilizador = params["id_Utilizador"] ?: ""
            if (tipoBusca == "criacao") {

                val id = obterID_Personagem()
                val nome = params["nome_personagem"] ?: ""
                val categoriaPrincipal = params["categoria_principal"] ?: ""
                val categoriaSecundaria = params["categoria_secundaria"] ?: ""
                val nivel = 1
                val armaEquipada = 1
                val coins = 1
                val progresso = 1
                val personagem = criarPersonagem(
                    id,
                    idUtilizador.toInt(),
                    nome,
                    categoriaPrincipal,
                    categoriaSecundaria,
                    nivel,
                    armaEquipada,
                    coins,
                    progresso
                )
                call.respond(ThymeleafContent("vila", mapOf("personagem" to personagem)))

            } else if (tipoBusca == "selecao") {
                val personagemSelecionado = params["personagemSelecionado"] ?: ""
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

        post("/perfilPersonagem") {
            val params = call.receiveParameters()
            val idPersonagem = params["personagem_ID"] ?:""
            val personagem = obterTodosPersonagens().find {it.id == idPersonagem.toInt()}


            val inventarioPersonagem: Item = Item(idPersonagem.toInt())
            val inventarioP = inventarioPersonagem.mostrarArmasInventarioPorID(idPersonagem.toInt())
            val nomeArmasPersonagem = inventarioPersonagem.mostarArmasInventarioNome(inventarioP)




            if (personagem != null) {
                val nomeArmaEquipada = obterTodasArmas().find {personagem.armaEquipada == it.id}
                val nomeArma = nomeArmaEquipada?.nome
                if (inventarioPersonagem != null && nomeArma != null) {
                    call.respond(ThymeleafContent("perfilPersonagem", mapOf("personagem" to personagem,
                        "inventarioNomes" to nomeArmasPersonagem,
                        "inventarioID" to inventarioP,
                        "nomeArma" to nomeArma)))
                }
            }
        }

        post("/equiparArma") {
            val params = call.receiveParameters()
            val idPersonagem = params["personagem_ID"]?:""
            val idArma = params["arma_ID"]?:""
            val personagem = obterTodosPersonagens().find {it.id == idPersonagem.toInt()}


            if (personagem != null) {
                val inventarioPersonagem: Item = Item(idPersonagem.toInt())
                val inventarioP = inventarioPersonagem.mostrarArmasInventarioPorID(idPersonagem.toInt())
                val nomeArmasPersonagem = inventarioPersonagem.mostarArmasInventarioNome(inventarioP)
                if (idPersonagem != null && idArma != null) {
                    val personagem = obterTodosPersonagens().find { it.id == idPersonagem.toInt() }
                    if (personagem != null) {
                        personagem.armaEquipada = idArma.toInt()
                        atualizarPersonagem("armaEquipada",personagem.armaEquipada.toString(),personagem)
                        call.respond(ThymeleafContent("perfilPersonagem", mapOf("personagem" to personagem,
                            "inventarioNomes" to nomeArmasPersonagem,
                            "inventarioID" to inventarioP)))
                    }
            }
                } else {
                    if (personagem != null) {
                        call.respond(
                            ThymeleafContent("perfilPersonagem", mapOf("personagem" to personagem,
                                    "erro" to "Não foi possivel equipar a arma"
                                )
                            )
                        )
                    }
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

        post("/combate") {
            val params        = call.receiveParameters()
            val personagemId  = params["personagem"]!!.toInt()
            val adversarioId  = params["adversario"]!!.toInt()

            val personagem = obterTodosPersonagens().find { it.id == personagemId }!!
            val adversario = obterTodosAdversarios().find { it.id == adversarioId }!!


            val (primeiroAtacante, vencedor, log) = Combate(personagem, adversario).comecarBatalha(false)

            call.respond(
                ThymeleafContent(
                    "combate",
                    mapOf(
                        "personagem"       to personagem,
                        "adversario"       to adversario,
                        "primeiroAtacante" to primeiroAtacante,
                        "vencedor"         to vencedor,
                        "logBatalha"       to log       // List<String>
                    )
                )
            )
        }


        post("/loja") {
            val params = call.receiveParameters()
            val idPersonagem = params["personagemIdLoja"]!!.toInt()
            val armasDisponiveis = mostrarTodasArmas()
            val personagem = obterTodosPersonagens().find { it.id == idPersonagem }
            val inventarioPersonagem: Item = Item(idPersonagem)
            val inventarioP = inventarioPersonagem.mostrarArmasInventarioPorID(idPersonagem)
            val nomeArmasPersonagem = inventarioPersonagem.mostarArmasInventarioNome(inventarioP)

            if (personagem != null) {
                call.respond(ThymeleafContent("loja", mapOf(
                "personagem" to personagem,
                "armasDisponiveis" to armasDisponiveis,
                "inventarioPersonagem" to inventarioP,
                 "inventarioPersonagemNomes" to nomeArmasPersonagem))) }
            else {
                println("Personagem ou Armas não encontradas")
            }
        }

        post("/comprarArma") {
            val params = call.receiveParameters()
            val personagem = params["personagemId"]?.toInt()
            val arma = params["armaId"]?.toInt()
            val armasDisponiveis = mostrarTodasArmas()
            val personagemCompleto = obterTodosPersonagens().find {it.id == personagem}


            if (arma != null && personagem != null) {
                comprarObjeto(arma,personagem)
            }

            if (personagem != null && personagemCompleto != null) {
                val inventarioPersonagem: Item = Item(personagem.toInt())
                val inventarioP = inventarioPersonagem.mostrarArmasInventarioPorID(personagem)
                val nomeArmasPersonagem = inventarioPersonagem.mostarArmasInventarioNome(inventarioP)
                println(nomeArmasPersonagem)
                call.respond(ThymeleafContent("loja", mapOf(
                    "personagem" to personagemCompleto,
                    "armasDisponiveis" to armasDisponiveis,
                    "inventarioPersonagem" to inventarioP,
                    "inventarioPersonagemNomes" to nomeArmasPersonagem)))}
            else {
                println("Personagem ou Armas não encontradas")
            }
        }

        post("/venderArma") {
            val params = call.receiveParameters()
            val personagem = params["personagemId"]?.toInt()
            val arma = params["armaId"]?.toInt()
            val armasDisponiveis = mostrarTodasArmas()
            val personagemCompleto = obterTodosPersonagens().find {it.id == personagem}
            if (arma != null && personagem != null) {
                venderObjeto(arma,personagem)
            }
            if (personagem != null && personagemCompleto != null) {
                val inventarioPersonagem: Item = Item(personagem.toInt())
                val inventarioP = inventarioPersonagem.mostrarArmasInventarioPorID(personagem)
                val nomeArmasPersonagem = inventarioPersonagem.mostarArmasInventarioNome(inventarioP)
                println(nomeArmasPersonagem)
                call.respond(ThymeleafContent("loja", mapOf(
                    "personagem" to personagemCompleto,
                    "armasDisponiveis" to armasDisponiveis,
                    "inventarioPersonagem" to inventarioP,
                    "inventarioPersonagemNomes" to nomeArmasPersonagem)))}
            else {
                println("Personagem ou Armas não encontradas")
            }
        }

        post("/taberna") {
            val params = call.receiveParameters()
            val personagemId = params["personagemIdTaberna"]?.toIntOrNull()
            val personagem = obterTodosPersonagens().find { it.id == personagemId }
            val missoes = obterTodasMissoes()
            var missoesDisponiveis = mutableListOf<Missao>()
            for (missao in missoes) {
                if (personagem != null) {
                    if (missao.nivel == personagem.nivel ) {
                        missoesDisponiveis.add(missao)
                    }
                   }
            }
            if (personagem != null) {
                val context = mutableMapOf<String, Any>(
                    "missoesDisponiveis" to missoesDisponiveis,
                    "personagem" to personagem
                )
                call.respond(ThymeleafContent("taberna", context))
            }
        }

        post("/comecarMissao") {
            val params = call.receiveParameters()
            val personagemId = params["personagem"]?.toIntOrNull()
            val missaoId = params["missao"]?.toIntOrNull()

            val personagem = obterTodosPersonagens().find { it.id == personagemId }

            val missoes = obterTodasMissoes()
            var missoesDisponiveis = mutableListOf<Missao>()
            for (missao in missoes) {
                if (personagem != null) {
                    if (missao.nivel == personagem.nivel ) {
                        missoesDisponiveis.add(missao)
                    }
                }
            }
            val missao = obterTodasMissoes().find { it.id == missaoId }

            if (personagem != null && missao != null) {
                val missaoEscolhida = Missao.começarMissao(missao, personagem)

                if (missaoEscolhida != null) {
                    val (resultadoCombate, duracao, expMissao) = missaoEscolhida
                    val logBatalha = resultadoCombate.third
                    val primeiroAtacante = resultadoCombate.first
                    val vencedor = resultadoCombate.second

                    call.respond(
                        ThymeleafContent(
                            "comecarMissao", mapOf(
                                "personagem" to personagem,
                                "missao" to missao,
                                "combate" to resultadoCombate,
                                "duracao" to duracao,
                                "expMissao" to expMissao,
                                "logBatalha" to logBatalha,
                                "primeiroAtacante" to primeiroAtacante,
                                "vencedor" to vencedor
                            )
                        )
                    )

                } else {
                    val context = mutableMapOf<String, Any>(
                        "missoesDisponiveis" to missoesDisponiveis,
                        "personagem" to personagem,
                        "erro" to "A personagem perdeu a batalha, não completando a missão"
                    )
                    call.respond(ThymeleafContent("taberna", context))
                }
            }
        }
    }
}
data class ThymeleafUser(val id: Int, val name: String)
