import java.rmi.Naming;
import java.util.Scanner;

/**
 * A classe do cliente. Cada jogador executa uma instância desta classe.
 * Ela se conecta ao servidor via RMI para interagir com o jogo.
 */

public class GoGameClient {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java GoGameClient <seu_id_de_jogador (1 ou 2)>");
            return;
        }

        try {
            int myPlayerID = Integer.parseInt(args[0]);
            // Procura pelo serviço remoto no RMI Registry local
            GoGameInterface game = (GoGameInterface) Naming.lookup("rmi://localhost/GoGameService");

            System.out.println("Conectado ao servidor. Voce e o Jogador " + myPlayerID + ".");
            System.out.println("Pecas: Jogador 1 = X (Preto), Jogador 2 = O (Branco)");
            System.out.println("Para passar a vez, digite -1 na linha.");

            // A anotação abaixo remove o aviso da IDE
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);

            while (true) {
                int currentPlayer = game.getCurrentPlayer();
                int[][] board = game.getBoard();
                displayBoard(board);

                System.out.println("Vez do Jogador: " + currentPlayer);

                if (currentPlayer == myPlayerID) {
                    System.out.print("Sua vez. Digite a linha: ");
                    int row = scanner.nextInt();

                    if (row == -1) {
                        String response = game.passTurn(myPlayerID);
                        System.out.println("Servidor: " + response);
                    } else {
                        System.out.print("Digite a coluna: ");
                        int col = scanner.nextInt();
                        String response = game.makeMove(row, col, myPlayerID);
                        System.out.println("Servidor: " + response);
                    }
                } else {
                    System.out.println("Aguardando a jogada do oponente...");
                    // Verifica o estado do jogo a cada 3 segundos
                    Thread.sleep(3000);
                }
                System.out.println("\n----------------------------------\n");
            }
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void displayBoard(int[][] board) {
        int boardSize = board.length;
        System.out.print("  ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < boardSize; j++) {
                char stone = '.';
                if (board[i][j] == 1)
                    stone = 'X'; // Preto
                else if (board[i][j] == 2)
                    stone = 'O'; // Branco
                System.out.print(stone + " ");
            }
            System.out.println();
        }
    }
}