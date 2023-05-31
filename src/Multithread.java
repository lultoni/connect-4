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
        int[] startPosition = new int[42];
        int[] faker = new int[]{position[0], position[1], position[2], position[3], position[4], position[5], position[6], position[7], position[8], position[9], position[10], position[11], position[12], position[13], position[14], position[15], position[16], position[17], position[18], position[19], position[20], position[21], position[22], position[23], position[24], position[25], position[26], position[27], position[28], position[29], position[30], position[31], position[32], position[33], position[34], position[35], position[36], position[37], position[38], position[39], position[40], position[41]};
        System.arraycopy(faker, 0, startPosition, 0, 42);
//        System.out.println(Arrays.equals(faker, this.position) + " before ");
        int val = search(faker, this.depth, this.maximizingPlayer, this.alpha, this.beta);
//        System.arraycopy(startPosition, 0, this.position, 0, 42);
//        System.out.println(Arrays.equals(faker, this.position) + " after ");
        return val;
    }

    private int search(int[] position, int depth, boolean maximizingPlayer, int alpha, int beta) {
        int evl = Board.getInstance().evaluate(position);
        if (depth == 0 || evl == 999999 || evl == -999999) {
            return evl / (this.depth - depth + 1);
        }
        if (!Board.getInstance().areFieldsLeft(position)) {
            return 0;
        }

        ArrayList<Integer> moves = Board.getInstance().legalMoves(position);
        if (maximizingPlayer) {
            int maxEval = -999999;
            for (int m: moves) {
                makeMove(m, true, position);
                int eval = search(position, depth - 1, false, alpha, beta);
                undoMove(m, position);
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
                makeMove(m, false, position);
                int eval = search(position, depth - 1, true, alpha, beta);
                undoMove(m, position);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private void makeMove(int column, boolean turn, int[] position) {
        int symbol;
        for (int i = 5; i >= 0; i--) {
            if (position[i * 6 + column - 1 + i] == 0) {
                if (turn) {
                    symbol = 1;
                } else {
                    symbol = -1;
                }
                position[i * 6 + column - 1 + i] = symbol;
                break;
            }
        }
    }

    private void undoMove(int column, int[] position) {
        for (int i = 0; i < 6; i++) {
            if (position[i * 6 + column - 1 + i] != 0) {
                position[i * 6 + column - 1 + i] = 0;
                break;
            }
        }
    }


}
