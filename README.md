# 🏰 **Kotlin Adventures** 
## Projeto de POO em Kotlin

### 👥 **Autores:**
* Daniel Sousa - 
* Daniel Vieira - 126844 


### 📜 **Descrição:**
Este projeto consiste num simulador de vida medieval onde o utilizador cria um personagem e toma decisões que impactam a sua jornada numa vila medieval. 
As escolhas do jogador influenciam a sua profissão, relacionamentos, participação em combates e envolvimento em eventos aleatórios.

### 🎯 **Objetivo:**
Desenvolver um jogo de simulação que permita ao utilizador experienciar a vida de um personagem na Idade Média.

### 🛠️ **Sistema:**
O sistema será estruturado com as seguintes classes principais:
* **Utilizador:** Representa o jogador que controla o personagem no jogo.
* **Personagem:** Representa o personagem do utilizador, incluindo atributos como nome, CategoriaPersonagem, CategoriaSecundaria e Inventario.
* **CategoriaPersonagem:** Define a classe do personagem (guerreiro, mago, arqueiro).
* **CategoriaSecundaria:** Define a profissão do personagem (ferreiro, agricultor, pescador, caçador).
* **Missao:** Representa tarefas ou desafios que o personagem pode realizar (caçar, entregar cartas, etc.).
* **Inventario:** Contém os itens que o personagem possui.
* **Item:** Define um item genérico com atributos como nome, tipo (arma, comida, poção) e efeito.
* **Arma:** Subclasse de Item, com atributos adicionais como dano, tipo e durabilidade.
* **RelacaoSocial:** Representa as relações do personagem com outros personagens e com a vila (amizade, romance, rivalidade).
* **Vila:** Representa o local onde o jogo se passa, incluindo informações sobre a população, locais e reputação do jogador.
* **Taberna:** Um local na vila onde o personagem pode descansar, obter informações ou contratar serviços.
* **SistemaDeCombate:** Lógica para gerenciar combates (ataque, defesa, vida).
* **SistemaDeJogo:** Gerencia o fluxo geral do jogo, incluindo menus e entrada do utilizador.

### ✨ **Funcionalidades:**
* Criação de personagem com escolha de profissão.
* Opções de ações: trabalhar, visitar a taberna, aceitar missões, combater
* Utilização e compra de itens.
* Combate.
* Evolução do personagem ao longo do tempo.

### 🔗 **Relações Entre as Classes:**
* A classe Personagem possui uma CategoriaSecundaria (profissão), um Inventario e uma lista de adversários derrotados.
* A classe Inventario contém múltiplos objetos Item (onde Arma é um tipo de Item).
* A classe Missao pode recompensar o jogador com um Item.
* A classe Vila é o ambiente principal onde o personagem vive, e Eventos podem ocorrer na vila.
* A classe SistemaDeCombate gere os combates entre personagens.
* A classe SistemaDeJogo gere o ciclo principal do jogo.

### 🚧 **Dificuldades Esperadas:**
No início, uma das dificuldades sentidas foi entender a lógica de passagem de informações entre os Backend e o Frontend,
as dificuldades foram ultrapassadas com a ajuda de tutoriais e documentação. 
A implementação de um sistema de combate, a gestão de inventário e a interação da loja também foram desafiadoras,
mas conseguimos implementar uma lógica que permite ao jogador interagir com o jogo de forma dinâmica.
