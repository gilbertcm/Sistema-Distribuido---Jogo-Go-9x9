# Jogo de Go Distribuído com Java RMI

![Java](https://img.shields.io/badge/language-Java-blue?style=for-the-badge&logo=java)
![Platform](https://img.shields.io/badge/platform-Desktop-orange?style=for-the-badge)
![Status](https://img.shields.io/badge/status-Concluído-green?style=for-the-badge)

Projeto acadêmico da disciplina de Sistemas Distribuídos que implementa uma versão básica do jogo de tabuleiro Go (9x9) para dois jogadores em uma arquitetura cliente-servidor, utilizando Java RMI para a comunicação remota.

---

## 📜 Descrição do Projeto

O objetivo deste projeto é demonstrar os conceitos de sistemas distribuídos através da criação de um jogo multiplayer. A aplicação consiste em um **servidor central** que gerencia toda a lógica e o estado do jogo, e dois **clientes**, um para cada jogador, que se conectam remotamente para participar da partida.

Toda a comunicação entre os clientes e o servidor é realizada através do **Java RMI (Remote Method Invocation)**, que permite que os clientes invoquem métodos no servidor como se fossem objetos locais, abstraindo a complexidade da comunicação em rede.

## ✨ Funcionalidades Implementadas

Conforme a especificação, o jogo implementa um conjunto básico de regras do Go:

* ♟️ **Colocar uma peça:** Posicionar uma peça no tabuleiro 9x9.
* ⏩ **Passar a vez:** Pular o turno sem colocar uma peça.
* ⚔️ **Captura de peça única:** Remover uma peça adversária do tabuleiro ao cercá-la completamente (lógica simplificada para peças individuais).

## 🚀 Tecnologias Utilizadas

* **Java:** Linguagem de programação principal do projeto.
* **RMI (Remote Method Invocation):** Middleware Java para a comunicação distribuída entre o servidor e os clientes.

## ⚙️ Como Executar o Projeto

Siga os passos abaixo para compilar e executar a aplicação localmente.

### Pré-requisitos

* **JDK (Java Development Kit)**, versão 8 ou superior, instalado e configurado corretamente no PATH do sistema.

### Passos para Execução

A ordem dos passos é **muito importante** para o correto funcionamento do RMI. Você precisará de, no mínimo, **três janelas de terminal/console**.

1.  **Clone o repositório** (ou simplesmente baixe os arquivos `.java` para uma pasta vazia):
    ```bash
    git clone https://github.com/https://github.com/gilbertcm/Sistema-Distribuido---Jogo-Go-9x9.git
    cd Sistema-Distribuido---Jogo-Go-9x9
    ```

2.  **Compile todos os arquivos Java:**
    Abra um terminal na pasta do projeto e execute:
    ```bash
    javac *.java
    ```

3.  **Inicie o RMI Registry (Terminal 1):**
    Este serviço de nomes deve ser o primeiro a ser executado.
    ```bash
    rmiregistry
    ```
    *Este terminal ficará ativo. Mantenha-o aberto.*

4.  **Inicie o Servidor do Jogo (Terminal 2):**
    O servidor se registrará no `rmiregistry`.
    ```bash
    java GoGameServer
    ```
    *Você verá a mensagem: "Servidor do Jogo Go iniciado e pronto para receber conexões."*

5.  **Inicie os Clientes (Terminais 3 e 4):**
    Cada jogador precisa de sua própria instância do cliente, com seu ID (1 ou 2).

    * **Para o Jogador 1 (Terminal 3):**
        ```bash
        java GoGameClient 1
        ```

    * **Para o Jogador 2 (Terminal 4):**
        ```bash
        java GoGameClient 2
        ```

Pronto! Agora os dois jogadores podem interagir com o jogo em seus respectivos terminais.

## 🎮 Exemplo de Uso

O terminal do jogador solicitará a linha e a coluna para a jogada. Para **passar a vez**, digite `-1` quando for solicitado a **linha**.

**Terminal do Jogador 1 (sua vez):**
```
Vez do Jogador: 1
Sua vez. Digite a linha: 4
Digite a coluna: 4
Servidor: Jogada realizada com sucesso.
```

**Terminal do Jogador 2 (após a jogada do oponente):**
```
Vez do Jogador: 2
  0 1 2 3 4 5 6 7 8
0 . . . . . . . . .
1 . . . . . . . . .
2 . . . . . . . . .
3 . . . . . . . . .
4 . . . . X . . . .
5 . . . . . . . . .
6 . . . . . . . . .
7 . . . . . . . . .
8 . . . . . . . . .
Vez do Jogador: 2
Sua vez. Digite a linha:
```

## 📂 Estrutura do Projeto

```
.
├── GoGameInterface.java   # O "contrato" remoto que define os métodos RMI.
├── GoGame.java            # A classe com a lógica e as regras internas do jogo.
├── GoGameServer.java      # O servidor que hospeda a instância do jogo e a registra no RMI.
└── GoGameClient.java      # O cliente que cada jogador utiliza para se conectar e jogar.
```

## 👤 Autor

* **Gilbert Carmo Macêdo**
* **GitHub:** `https://github.com/gilbertcm`

---
