import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class Multithread implements Callable<Integer> {

    int[] position;
    int depth;
    boolean maximizingPlayer;
    int alpha;
    int beta;

    public Multithread(int[] position, int depth, boolean maximizingPlayer, int alpha, int beta) {
        this.position = position;
        this.depth = depth;
        this.maximizingPlayer = maximizingPlayer;
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public Integer call() {
        return search(this.position, this.depth, this.maximizingPlayer, this.alpha, this.beta);
    }

    private int search(int[] position, int depth, boolean maximizingPlayer, int alpha, int beta) {
        if (!Board.getInstance().areFieldsLeft(position)) {
            return 0;
        }
        int evl = Board.getInstance().evaluate(position);
        if (depth == 0 || evl == 999999 || evl == -999999) {
            return evl / (this.depth - depth + 1);
        }

        ArrayList<Integer> moves = Board.getInstance().legalMoves(position);
        if (maximizingPlayer) {
            int maxEval = -999999;
            for (int m: moves) {
                makeMove(m, true);
                int eval = search(position, depth - 1, false, alpha, beta);
                undoMove(m);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = 999999;
            for (int m: moves) {
                makeMove(m, false);
                int eval = search(position, depth - 1, true, alpha, beta);
                undoMove(m);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private void makeMove(int column, boolean turn) {
        int symbol;
        for (int i = 5; i >= 0; i--) {
            if (this.position[i * 6 + column - 1 + i] == 0) {
                if (turn) {
                    symbol = 1;
                } else {
                    symbol = -1;
                }
                this.position[i * 6 + column - 1 + i] = symbol;
                break;
            }
        }
    }

    private void undoMove(int column) {
        for (int i = 0; i < 6; i++) {
            if (this.position[i * 6 + column - 1 + i] != 0) {
                this.position[i * 6 + column - 1 + i] = 0;
                break;
            }
        }
    }


}
