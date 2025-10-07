import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A interface remota (o "contrato") que define os métodos que o cliente
 * pode invocar no servidor.
 * Todo método aqui deve declarar que pode lançar uma RemoteException.
 */
public interface GoGameInterface extends Remote {

    /**
     * Obtém o estado atual do tabuleiro.
     * 
     * @return Uma matriz 2D de inteiros representando o tabuleiro.
     */
    int[][] getBoard() throws RemoteException;

    /**
     * Tenta realizar uma jogada em nome de um jogador.
     * 
     * @param row      A linha da jogada.
     * @param col      A coluna da jogada.
     * @param playerID O ID do jogador (1 ou 2).
     * @return Uma mensagem de status sobre a jogada.
     */
    String makeMove(int row, int col, int playerID) throws RemoteException;

    /**
     * Permite que um jogador passe a sua vez.
     * 
     * @param playerID O ID do jogador que está passando a vez.
     * @return Uma mensagem de status.
     */
    String passTurn(int playerID) throws RemoteException;

    /**
     * Obtém o ID do jogador cuja vez é agora.
     * 
     * @return O ID do jogador atual (1 ou 2).
     */
    int getCurrentPlayer() throws RemoteException;
}