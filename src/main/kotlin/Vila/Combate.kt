package com.example.Vila

import com.example.Adversários.Adversário
import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion.atualizarPersonagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
import com.example.Criacao_personagem.Personagem.Companion.passarNivel
import com.example.Menu.Arma.Companion.obterTodasArmas
import kotlinx.serialization.Serializable
import kotlin.random.Random

class Combate (var personagem : Personagem, var inimigo : Adversário) {

    override fun toString(): String {
        return "  ID do Personagem - ${personagem.id}" +
                " ID do Adversário - ${inimigo.id}" +
                "   Personagem: ${personagem.nome} :" +
                "       Categoria Principal : ${personagem.categoriaPrincipal}" +
                "       Categoria Secundaria : ${personagem.categoriaSecundaria}" +
                "       Nível: $personagem.nivel" +
                "       Arma: $personagem.inventario" +
                "" +
                "   Adversario: ${inimigo.nome} :" +
                "       Categoria Principal : ${inimigo.categoriaPrincipal}" +
                "       Categoria Secundaria : ${inimigo.categoriaSecundaria}" +
                "       Nível: $inimigo.nivel" +
                "       Arma: $inimigo.inventario"
    }

    fun comecarBatalha(missao: Boolean): Triple<String, String, List<String>> {
        val armaPersonagem = obterTodasArmas().find { it.id == personagem.armaEquipada }
        val armaAdversario = obterTodasArmas().find { it.id == inimigo.arma }
        var vitorioso = ""
        var personagemPV = 100
        var adversarioPV = 100
        var primeiroAtacante = ""
        val combateLog = mutableListOf<String>()
        val coinFlip = (1..2).random()
        if (armaPersonagem != null && armaAdversario != null) {
            if (coinFlip == 1) {
                primeiroAtacante = "O personagem atacará primeiro!"
                combateLog.add(primeiroAtacante)
                while (personagemPV > 0 && adversarioPV > 0) {
                    adversarioPV -= armaPersonagem.dano
                    combateLog.add("Personagem ataca com ${armaPersonagem.nome} causando ${armaPersonagem.dano} de dano. Vida do adversário: $adversarioPV")
                    if (adversarioPV <= 0) {
                        vitorioso = "O personagem é vitorioso"
                        atualizarPersonagem("nivel", passarNivel(20, personagem), personagem)
                        atualizarPersonagem("coins", "50", personagem)
                        if (!missao) {
                            atualizarPersonagem("progresso", (personagem.progresso + 1).toString(), personagem)
                        }
                        break
                    }
                    personagemPV -= armaAdversario.dano
                    combateLog.add("Adversário ataca com ${armaAdversario.nome} causando ${armaAdversario.dano} de dano. Vida do personagem: $personagemPV")
                    if (personagemPV <= 0) {
                        vitorioso = "O adversário é vitorioso"
                        break
                    }
                }
            } else {
                primeiroAtacante = "O adversário atacará primeiro!"
                combateLog.add(primeiroAtacante)
                while (personagemPV > 0 && adversarioPV > 0) {
                    personagemPV -= armaAdversario.dano
                    combateLog.add("Adversário ataca com ${armaAdversario.nome} causando ${armaAdversario.dano} de dano. Vida do personagem: $personagemPV")
                    if (personagemPV <= 0) {
                        vitorioso = "O adversário é vitorioso"
                        break
                    }
                    adversarioPV -= armaPersonagem.dano
                    combateLog.add("Personagem ataca com ${armaPersonagem.nome} causando ${armaPersonagem.dano} de dano. Vida do adversário: $adversarioPV")
                    if (adversarioPV <= 0) {
                        vitorioso = "O personagem é vitorioso"
                        atualizarPersonagem("nivel", passarNivel(20, personagem), personagem)
                        atualizarPersonagem("coins", "50", personagem)
                        if (missao != true) {
                            atualizarPersonagem("progresso", (personagem.progresso + 1).toString(), personagem)
                        }
                        break
                    }
                }
            }
        }
        return Triple(primeiroAtacante, vitorioso, combateLog)
    }

}
