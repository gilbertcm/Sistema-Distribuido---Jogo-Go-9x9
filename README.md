1. Visão Geral do Projeto
Este documento detalha a implementação de uma versão básica e distribuída do jogo de tabuleiro Go. O objetivo principal do projeto é demonstrar os conceitos de sistemas distribuídos através da tecnologia Java RMI, permitindo que dois jogadores participem da mesma partida a partir de máquinas diferentes (ou processos diferentes na mesma máquina).

A aplicação consiste em um servidor central que gerencia o estado do jogo e dois clientes, um para cada jogador, que se conectam ao servidor para visualizar o tabuleiro e realizar suas jogadas. O sistema foi projetado para ser simples, focando na mecânica da comunicação remota em vez de implementar todas as complexas regras do Go.

2. Arquitetura da Aplicação
A aplicação segue o modelo Cliente-Servidor e utiliza Java RMI como middleware para a comunicação.

Modelo Cliente-Servidor: Um processo único (o Servidor) atua como a autoridade central do jogo. Ele mantém o estado do tabuleiro, controla os turnos e valida as jogadas. Múltiplos processos (os Clientes) se conectam a este servidor para participar do jogo. Toda a lógica e estado do jogo residem no servidor, garantindo consistência.

Java RMI (Remote Method Invocation): É a tecnologia que permite que um objeto em uma Máquina Virtual Java (JVM), o cliente, invoque métodos em um objeto em outra JVM, o servidor. O RMI abstrai a complexidade da comunicação de rede, permitindo que o desenvolvedor chame um método remoto quase como se fosse uma chamada de método local.

O fluxo de comunicação é o seguinte:

O Servidor cria uma instância do jogo e a registra em um serviço de nomes chamado RMI Registry.

O Cliente consulta o RMI Registry para "encontrar" o objeto do jogo remoto.

O RMI Registry retorna ao cliente um "stub" (um proxy), que tem a mesma interface do objeto remoto.

O Cliente chama métodos neste stub (ex: makeMove()).

O RMI se encarrega de enviar essa chamada pela rede, executá-la no Servidor e retornar o resultado ao Cliente.

3. Descrição dos Componentes (Arquivos Fonte)
O projeto é composto por quatro arquivos Java principais:

a. GoGameInterface.java
Este arquivo é o "contrato" da comunicação remota. É uma interface Java que estende java.rmi.Remote e define quais métodos do servidor podem ser invocados pelos clientes.

Propósito: Desacoplar o cliente do servidor. O cliente só precisa conhecer esta interface para interagir com o jogo, sem precisar saber os detalhes da sua implementação no servidor.

Métodos Definidos:

getBoard(): Retorna o estado atual do tabuleiro (uma matriz 2D).

makeMove(row, col, playerID): Envia uma tentativa de jogada para o servidor.

passTurn(playerID): Envia a ação de passar o turno.

getCurrentPlayer(): Retorna o ID do jogador da vez.

b. GoGame.java
Esta é a classe que contém a lógica pura do jogo. Ela é o "cérebro" da aplicação.

Propósito: Gerenciar o estado interno do jogo, como a posição das peças no tabuleiro, de quem é a vez de jogar e a aplicação das regras. Esta classe não tem conhecimento sobre rede ou RMI, o que a torna reutilizável e fácil de testar.

Responsabilidades:

Manter a matriz 9x9 do tabuleiro.

Validar se uma jogada é legal (dentro dos limites, posição vazia).

Alternar o turno entre os jogadores.

Verificar e executar a captura de peças inimigas.

c. GoGameServer.java
Esta classe é a implementação do servidor. Ela atua como a ponte entre a lógica do jogo e a rede.

Propósito: Instanciar o jogo, expô-lo na rede via RMI e processar as chamadas remotas dos clientes.

Funcionamento:

Implementa a GoGameInterface.

Cria e mantém uma instância privada do GoGame.

Os métodos implementados da interface (como makeMove) recebem as chamadas dos clientes e as delegam para a instância do GoGame.

No seu método main, ele cria uma instância de si mesmo e a registra no RMI Registry com um nome público ("GoGameService"), tornando-se visível para os clientes.

d. GoGameClient.java
Esta é a aplicação que cada jogador executa em sua máquina.

Propósito: Fornecer uma interface de linha de comando para o jogador interagir com o jogo.

Funcionamento:

No método main, ele procura pelo "GoGameService" no RMI Registry para obter a conexão com o servidor.

Entra em um loop infinito que constitui o ciclo do jogo.

A cada iteração do loop, ele chama os métodos remotos getBoard() e getCurrentPlayer() para obter o estado mais recente do jogo e o exibe no console.

Se for a sua vez, ele aguarda a entrada do usuário (coordenadas da jogada ou comando para passar a vez).

Envia a jogada do usuário para o servidor através da chamada remota correspondente (makeMove() ou passTurn()).

Se não for a sua vez, ele "dorme" por 3 segundos antes de verificar novamente, para evitar sobrecarregar o servidor com requisições (polling).

4. Regras do Jogo Implementadas
Conforme a especificação do trabalho, foram implementados três tipos de jogadas:

Colocar uma Peça: O jogador especifica uma linha e uma coluna para posicionar sua peça. O sistema valida se a posição está dentro do tabuleiro e se não está ocupada.

Passar a Vez: O jogador pode optar por não colocar uma peça e passar o turno para o oponente.

Captura de Peça Única: Foi implementada uma lógica simplificada de captura. Se uma peça recém-colocada remove a última "liberdade" (espaço vazio adjacente) de uma peça adversária única, essa peça adversária é removida do tabuleiro. A lógica não cobre a captura de grupos de peças.

5. Como Compilar e Executar
Siga os passos abaixo para testar a aplicação. É necessário ter o JDK (Java Development Kit) instalado e configurado nas variáveis de ambiente.

Pré-requisitos
JDK 8 ou superior.

Os 4 arquivos .java na mesma pasta.

Passo 1: Compilação
Abra um terminal ou prompt de comando na pasta do projeto e execute o seguinte comando para compilar todos os arquivos:

Bash

javac *.java
Passo 2: Execução (A Ordem é Importante)
Você precisará de, no mínimo, três janelas de terminal abertas na pasta do projeto.

No Terminal 1 - Iniciar o RMI Registry:
Este serviço atua como um "catálogo telefônico" para as aplicações RMI.

Bash

rmiregistry
Observação: Este terminal ficará ativo, mas sem novas mensagens. Deixe-o aberto durante toda a execução.

No Terminal 2 - Iniciar o Servidor:
Este comando inicia o servidor do jogo, que se registrará no rmiregistry.

Bash

java GoGameServer
Você verá a mensagem "Servidor do Jogo Go iniciado..."

No Terminal 3 - Iniciar o Cliente do Jogador 1:

Bash

java GoGameClient 1
O jogo começará, e o terminal solicitará a jogada do Jogador 1.

Em um 4º Terminal - Iniciar o Cliente do Jogador 2:

Bash

java GoGameClient 2
Este terminal mostrará o tabuleiro e aguardará a jogada do Jogador 1.

Agora os dois jogadores podem se alternar, inserindo jogadas em seus respectivos terminais.

6. Possíveis Melhorias Futuras
Interface Gráfica (GUI): Substituir a interface de linha de comando por uma interface gráfica usando Swing ou JavaFX para uma experiência de usuário mais rica.

Lógica de Captura Completa: Implementar a regra completa de captura do Go, que lida com grupos de peças e suas liberdades.

Detecção de Fim de Jogo: Adicionar lógica para detectar o fim da partida (quando ambos os jogadores passam a vez consecutivamente) e calcular a pontuação.

Comunicação Otimizada: Em vez do cliente perguntar repetidamente ao servidor pelo estado do jogo (polling), o servidor poderia notificar ativamente os clientes sobre mudanças, utilizando padrões como Observer.

Lobby de Jogos: Criar um sistema onde múltiplos jogos possam ser criados e jogadores possam escolher em qual partida entrar.
