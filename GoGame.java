/**
 * Esta classe contém toda a lógica do jogo.
 * Ela não tem conhecimento sobre RMI ou rede, apenas sobre as regras do Go.
 */
public class GoGame {
    private int[][] board; // 0 = vazio, 1 = jogador 1 (Preto), 2 = jogador 2 (Branco)
    private int currentPlayer;
    private final int boardSize;

    public GoGame(int size) {
        this.boardSize = size;
        this.board = new int[size][size];
        this.currentPlayer = 1; // Jogador 1 (Preto) sempre começa
    }

    public int[][] getBoardState() {
        return this.board;
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == 1) ? 2 : 1;
    }

    /**
     * Método principal para tentar colocar uma peça no tabuleiro.
     * 
     * @return true se a jogada foi válida, false caso contrário.
     */
    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != 0) {
            return false; // Jogada inválida (fora do tabuleiro ou local ocupado)
        }

        board[row][col] = currentPlayer;

        // Após colocar a peça, verifica se alguma peça do oponente foi capturada
        checkForCaptures(row, col);

        switchPlayer();
        return true;
    }

    /**
     * Verifica as peças adjacentes à jogada recém-feita para possíveis capturas.
     */
    private void checkForCaptures(int row, int col) {
        int opponent = (currentPlayer == 1) ? 2 : 1;

        // Verifica o vizinho de cima
        if (row > 0 && board[row - 1][col] == opponent) {
            if (!hasLiberty(row - 1, col)) {
                board[row - 1][col] = 0; // Captura!
            }
        }
        // Verifica o vizinho de baixo
        if (row < boardSize - 1 && board[row + 1][col] == opponent) {
            if (!hasLiberty(row + 1, col)) {
                board[row + 1][col] = 0; // Captura!
            }
        }
        // Verifica o vizinho da esquerda
        if (col > 0 && board[row][col - 1] == opponent) {
            if (!hasLiberty(row, col - 1)) {
                board[row][col - 1] = 0; // Captura!
            }
        }
        // Verifica o vizinho da direita
        if (col < boardSize - 1 && board[row][col + 1] == opponent) {
            if (!hasLiberty(row, col + 1)) {
                board[row][col + 1] = 0; // Captura!
            }
        }
    }

    /**
     * Verifica se uma peça em uma dada posição tem alguma "liberdade"
     * (um espaço vazio adjacente).
     * Esta é uma versão SIMPLIFICADA da regra para uma única peça.
     * 
     * @return true se a peça tiver pelo menos uma liberdade, false caso contrário.
     */
    private boolean hasLiberty(int row, int col) {
        // Cima
        if (row > 0 && board[row - 1][col] == 0)
            return true;
        // Baixo
        if (row < boardSize - 1 && board[row + 1][col] == 0)
            return true;
        // Esquerda
        if (col > 0 && board[row][col - 1] == 0)
            return true;
        // Direita
        if (col < boardSize - 1 && board[row][col + 1] == 0)
            return true;

        return false; // Nenhuma liberdade encontrada
    }
}