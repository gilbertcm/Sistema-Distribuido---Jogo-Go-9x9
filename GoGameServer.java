import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A classe do servidor. Ela implementa a interface remota e gerencia
 * a instância do jogo. Fica aguardando chamadas dos clientes.
 */
public class GoGameServer extends UnicastRemoteObject implements GoGameInterface {

    private final GoGame game; // O servidor possui uma instância da lógica do jogo

    protected GoGameServer() throws RemoteException {
        super();
        game = new GoGame(9); // Inicia um novo jogo 9x9
    }

    @Override
    public int[][] getBoard() throws RemoteException {
        return game.getBoardState();
    }

    @Override
    public String makeMove(int row, int col, int playerID) throws RemoteException {
        if (game.getCurrentPlayer() != playerID) {
            return "Não é a sua vez!";
        }
        boolean success = game.makeMove(row, col);
        if (success) {
            return "Jogada realizada com sucesso.";
        } else {
            return "Jogada inválida (posição ocupada ou fora do tabuleiro).";
        }
    }

    @Override
    public String passTurn(int playerID) throws RemoteException {
        if (game.getCurrentPlayer() != playerID) {
            return "Não é a sua vez de passar!";
        }
        game.switchPlayer();
        return "Você passou a vez.";
    }

    @Override
    public int getCurrentPlayer() throws RemoteException {
        return game.getCurrentPlayer();
    }

    public static void main(String[] args) {
        try {
            GoGameServer server = new GoGameServer();
            // Registra o servidor no RMI Registry com o nome "GoGameService"
            Naming.rebind("GoGameService", server);
            System.out.println("Servidor do Jogo Go iniciado e pronto para receber conexoes.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}