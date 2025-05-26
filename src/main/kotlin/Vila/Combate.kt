package com.example.Vila

import com.example.Adversários.Adversário
import com.example.Criacao_personagem.Personagem
import com.example.Criacao_personagem.Personagem.Companion.obterTodosPersonagens
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

        fun comecarBatalha(): String {
            val armaPersonagem = obterTodasArmas().find { it.id == personagem.inventario[0] }
            val armaAdversario = obterTodasArmas().find { it.id == inimigo.arma }
            var vitorioso: String = ""
            var personagemPV: Int = 100
            var adversarioPV: Int = 100
            var primeiroAtacante: String = ""
            val coinFlip = (1..3).random()

            if (armaPersonagem != null) {
                if (armaAdversario != null) {
                    if (coinFlip == 1) {
                        primeiroAtacante = "O personagem atacará primeiro!"
                        while (personagemPV != 0 && adversarioPV != 0) {
                            adversarioPV -= armaPersonagem.dano
                            println(adversarioPV)
                            personagemPV -= armaAdversario.dano
                            println(personagemPV)
                        }
                        if (adversarioPV == 0) {
                            vitorioso = "O personagem é vitorioso"
                        } else {
                            vitorioso = "O adversario é vitorioso"
                        }
                    } else if (coinFlip == 2) {
                        primeiroAtacante = "O Adversário atacará primeiro"
                        while (personagemPV != 0 && adversarioPV != 0) {
                            personagemPV -= armaAdversario.dano
                            println(adversarioPV)
                            adversarioPV -= armaPersonagem.dano
                            println(personagemPV)
                        }
                        if (adversarioPV == 0) {
                            vitorioso = "O personagem é vitorioso"
                        } else {
                            vitorioso = "O adversario é vitorioso"
                        }
                    }
                }
            }
            return primeiroAtacante + " /n " + vitorioso
        }
}
