import java.util.Scanner;

public class Game {
    private Board board;
    private MCTS mcts;

    public Game() {
        this.board = Board.getInstance();
        this.mcts = new MCTS();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!board.gameOver()) {
            System.out.println("Current state:");
            board.printBoard();

            System.out.print("Enter a move: ");
            int userMove = scanner.nextInt();
            while (!board.legalMoves().contains(userMove)) {
                System.out.println("Illegal move");
                System.out.print("Enter a move: ");
                userMove = scanner.nextInt();
            }

            board.makeMove(userMove, board.getTurn());
            mcts.move(userMove);

            board.printBoard();

            if (!board.gameOver()) {
                System.out.println("Player one won!");
                break;
            }

            System.out.println("Thinking...");

            mcts.search(8);
            int numRollouts = mcts.numRollouts;
            long runTime = mcts.runTime;
            System.out.println("Statistics: " + numRollouts + " rollouts in " + runTime + " seconds");
            int move = mcts.bestMove();

            System.out.println("MCTS chose move: " + move);

            board.makeMove(userMove, board.getTurn());
            mcts.move(move);

            if (!board.gameOver()) {
                System.out.println("Player two won!");
                break;
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        System.out.println("Monte Carlo Tree Search Test");
        Game game = new Game();
        game.play();
    }
}
