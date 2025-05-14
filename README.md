# Simulador_poo
Projeto final de POO - Kotlin


Autores:
- Daniel Vieira
- Daniel Sousa

Descrição:
Simulador de Jogo Medieval
O utilizador cria um personagem e onde pode tomar decisões que afetam sua vida em uma vila medieval: profissão, relacionamentos entre personagens, combates, participação em eventos aleatórios.

Objetivo:
Desenvolver um jogo de simulação em que simule uma vida medieval de uma personagem.

Sistema:
Estrutura Inicial de Classes
Proposta de organização das classes que iram ser utilizadas no projeto:
1.	Personagem – nome, idade, CategoriaPersonagem, CategoriaSecundaria, Inventario, TempoDeJogo
2.	CategoriaPersonagem – guerreiro, mago, aqueiro
3.	CategoriaSecundaria - ferreiro, agricultor, pescador, caçador
4.	Missao – pequenas tarefas ou desafios (ex: caçar, entregar carta, etc.).
5.	Inventario – itens que o personagem carrega.
6.	Item – nome, tipo (arma, comida, poção), efeito.
7.	Arma – dano, tipo, durabilidade.
8.	RelacaoSocial – relação com outras personagens e com a vila em geral (amizade, romance, rivalidade).
9.	Evento – algo aleatório que acontece (festa na vila, ataque de bandidos, doença).
10.	Vila – local onde tudo se passa (população, locais, reputação do jogador).
11.	Taverna – um lugar onde o personagem pode descansar, ouvir rumores ou contratar serviços.
12.	SistemaDeCombate – lógica para combates (ataque, defesa, vida).
13.	SistemaDeJogo – gerência o fluxo do jogo, menus, entrada do utilizador.


Funcionalidades:
. Criar personagem (com escolha de profissão).
. Escolher o que fazer: trabalhar, ir à taverna, aceitar uma missão, explorar.
. Participar de eventos aleatórios.
. Usar/comprar itens.
. Entrar em combate.
. Aumentar reputação ou criar inimizades.
. Evoluir o personagem ao longo do tempo.

Relações Entre as Classes:
. Personagem tem uma Profissao, um Inventario, uma lista de RelacaoSocial.
. Inventario contém múltiplos Item (e Arma é um tipo de Item).
. Missao pode dar como recompensa um Item.
. Vila é o local geral onde o personagem vive, e Evento podem ocorrer nela.
. SistemaDeCombate lida com combates entre dois personagens.
. SistemaDeJogo gerencia o ciclo principal da aplicação.

Dificuldades esperadas:
O que vai ser mais difícil em termos de implementação.
