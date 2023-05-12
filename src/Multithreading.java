import java.util.ArrayList;

public class Multithreading extends Thread {


    int[] playingBoard;
    int counter = 0;
    int depth;
    boolean maximizingPlayer;
    int alpha;
    int beta;

    public Multithreading(int[] board, int depth, boolean maximizingPlayer, int alpha, int beta) {
       this.playingBoard = board;
       this.depth = depth;
       this.maximizingPlayer = maximizingPlayer;
       this.alpha = alpha;
       this.beta = beta;
    }
    @Override
    public void run() {
        String out = "~~~~~~~~~~\nSearch (" + depth + "): " + search(depth, maximizingPlayer, alpha, beta) + " | PS: " + counter + "\n" + boardString() + "~~~~~~~~~~";
        System.out.println(out);
    }

    private String boardString() {
        int start = 0;
        int stop = 7;
        String out = "";
        for (int i = 0; i < 6; i++) {
            for (int j = start; j < stop; j++) {
                if (j !=  start) {
                    out += " ";
                }
                if (this.playingBoard[i * 6 + j] == 0) {
                    out += "-";
                } else if (this.playingBoard[i * 6 + j] == 1) {
                    out += "X";
                } else if (this.playingBoard[i * 6 + j] == -1) {
                    out += "O";
                }
            }
            out += "\n";
            start++;
            stop++;
        }
        return out;
    }

    private int search(int depth, boolean maximizingPlayer, int alpha, int beta) { // Transposition table
        this.counter++;
//        System.out.println("---------------\nc: " + counter + " | d: " + depth + " | mP: " + maximizingPlayer + " | a: " + alpha + " | b: " + beta);
        int evl = evaluate();
        if (depth == 0 || evl == 999999 || evl == -999999) {
            return evl / (this.depth - depth + 1);
//            return evl;
        }

        ArrayList<Integer> moves = legalMoves();
//        order(moves);
        if (maximizingPlayer) {
            int maxEval = -999999;
            for (int m: moves) {
                makeMove(m, true);
                int eval = search(depth - 1, false, alpha, beta);
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
                int eval = search(depth - 1, true, alpha, beta);
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
            if (this.playingBoard[i * 6 + column - 1 + i] == 0) {
                if (turn) {
                    symbol = 1;
                } else {
                    symbol = -1;
                }
                this.playingBoard[i * 6 + column - 1 + i] = symbol;
                break;
            }
        }
    }

    private void undoMove(int column) {
        for (int i = 0; i < 6; i++) {
            if (this.playingBoard[i * 6 + column - 1 + i] != 0) {
                this.playingBoard[i * 6 + column - 1 + i] = 0;
                break;
            }
        }
    }

    private int evaluate() {
        // winner eval
        final int winnerBlack = -999999;
        final int winnerWhite =  999999;
        int advantage = 0;
        // Check for horizontal winner
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.playingBoard[i * 6 + j + i] == 0) {
                    continue;
                }
                switch (this.playingBoard[i * 6 + j + i] + this.playingBoard[i * 6 + j + 1 + i] + this.playingBoard[i * 6 + j + 2 + i] + this.playingBoard[i * 6 + j + 3 + i]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += 1000;
                    case -3 -> advantage -= 1000;
                }
            }
        }
        // Check for vertical winner
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.playingBoard[i + j * 7] == 0) {
                    continue;
                }
                switch (this.playingBoard[i + j * 7] + this.playingBoard[i + j * 7 + 7] + this.playingBoard[i + j * 7 + 14] + this.playingBoard[i + j * 7 + 21]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += 1000;
                    case -3 -> advantage -= 1000;
                }
            }
        }
        // Check for diagonal winner
        // Left Top Right Bottom
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.playingBoard[j * 7 + i] == 0) {
                    continue;
                }
                switch (this.playingBoard[j * 7 + i] + this.playingBoard[j * 7 + i + 8] + this.playingBoard[j * 7 + i + 16] + this.playingBoard[j * 7 + i + 24]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += 1000;
                    case -3 -> advantage -= 1000;
                }
            }
        }
        // Left Bottom Right Top
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.playingBoard[j * 7 + i + 3] == 0) {
                    continue;
                }
                switch (this.playingBoard[j * 7 + i + 3] + this.playingBoard[j * 7 + i + 3 + 6] + this.playingBoard[j * 7 + i + 3 + 12] + this.playingBoard[j * 7 + i + 3 + 18]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += 1000;
                    case -3 -> advantage -= 1000;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.playingBoard[i * 6 + j + i] == 0 && this.playingBoard[i * 6 + j + 4 + i] == 0) {
                    if (this.playingBoard[i * 6 + j + 1 + i] + this.playingBoard[i * 6 + j + 2 + i] + this.playingBoard[i * 6 + j + 3 + i] == 3) {
                        advantage += 4000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] + this.playingBoard[i * 6 + j + 2 + i] + this.playingBoard[i * 6 + j + 3 + i] == -3) {
                        advantage -= 4000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] == 1 && this.playingBoard[i * 6 + j + 2 + i] == 1 && this.playingBoard[i * 6 + j + 3 + i] == 0) {
                        advantage += 2000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] == 1 && this.playingBoard[i * 6 + j + 2 + i] == 0 && this.playingBoard[i * 6 + j + 3 + i] == 1) {
                        advantage += 2000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] == 0 && this.playingBoard[i * 6 + j + 2 + i] == 1 && this.playingBoard[i * 6 + j + 3 + i] == 1) {
                        advantage += 2000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] == -1 && this.playingBoard[i * 6 + j + 2 + i] == -1 && this.playingBoard[i * 6 + j + 3 + i] == 0) {
                        advantage -= 2000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] == -1 && this.playingBoard[i * 6 + j + 2 + i] == 0 && this.playingBoard[i * 6 + j + 3 + i] == -1) {
                        advantage -= 2000;
                    } else if (this.playingBoard[i * 6 + j + 1 + i] == 0 && this.playingBoard[i * 6 + j + 2 + i] == -1 && this.playingBoard[i * 6 + j + 3 + i] == -1) {
                        advantage -= 2000;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (this.playingBoard[j * 7 + i] == 0 && this.playingBoard[j * 7 + i + 32] == 0) {
                    if (this.playingBoard[j * 7 + i + 8] + this.playingBoard[j * 7 + i + 16] + this.playingBoard[j * 7 + i + 24] == 3) {
                        advantage += 4000;
                    } else if (this.playingBoard[j * 7 + i + 8] + this.playingBoard[j * 7 + i + 16] + this.playingBoard[j * 7 + i + 24] == -3) {
                        advantage -= 4000;
                    } else if (this.playingBoard[j * 7 + i + 8] == 1 && this.playingBoard[j * 7 + i + 16] == 1 && this.playingBoard[j * 7 + i + 24] == 0) {
                        advantage += 2000;
                    } else if (this.playingBoard[j * 7 + i + 8] == 1 && this.playingBoard[j * 7 + i + 16] == 0 && this.playingBoard[j * 7 + i + 24] == 1) {
                        advantage += 2000;
                    } else if (this.playingBoard[j * 7 + i + 8] == 0 && this.playingBoard[j * 7 + i + 16] == 1 && this.playingBoard[j * 7 + i + 24] == 1) {
                        advantage += 2000;
                    } else if (this.playingBoard[j * 7 + i + 8] == -1 && this.playingBoard[j * 7 + i + 16] == -1 && this.playingBoard[j * 7 + i + 24] == 0) {
                        advantage -= 2000;
                    } else if (this.playingBoard[j * 7 + i + 8] == -1 && this.playingBoard[j * 7 + i + 16] == 0 && this.playingBoard[j * 7 + i + 24] == -1) {
                        advantage -= 2000;
                    } else if (this.playingBoard[j * 7 + i + 8] == 0 && this.playingBoard[j * 7 + i + 16] == -1 && this.playingBoard[j * 7 + i + 24] == -1) {
                        advantage -= 2000;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (this.playingBoard[j * 7 + i + 4] == 0 && this.playingBoard[j * 7 + i + 4 + 24] == 0) {
                    if (this.playingBoard[j * 7 + i + 4 + 6] + this.playingBoard[j * 7 + i + 4 + 12] + this.playingBoard[j * 7 + i + 4 + 18] == 3) {
                        advantage += 4000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] + this.playingBoard[j * 7 + i + 4 + 12] + this.playingBoard[j * 7 + i + 4 + 18] == -3) {
                        advantage -= 4000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] == 1 && this.playingBoard[j * 7 + i + 4 + 12] == 1 && this.playingBoard[j * 7 + i + 4 + 18] == 0) {
                        advantage += 2000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] == 1 && this.playingBoard[j * 7 + i + 4 + 12] == 0 && this.playingBoard[j * 7 + i + 4 + 18] == 1) {
                        advantage += 2000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] == 0 && this.playingBoard[j * 7 + i + 4 + 12] == 1 && this.playingBoard[j * 7 + i + 4 + 18] == 1) {
                        advantage += 2000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] == -1 && this.playingBoard[j * 7 + i + 4 + 12] == -1 && this.playingBoard[j * 7 + i + 4 + 18] == 0) {
                        advantage -= 2000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] == -1 && this.playingBoard[j * 7 + i + 4 + 12] == 0 && this.playingBoard[j * 7 + i + 4 + 18] == -1) {
                        advantage -= 2000;
                    } else if (this.playingBoard[j * 7 + i + 4 + 6] == 0 && this.playingBoard[j * 7 + i + 4 + 12] == -1 && this.playingBoard[j * 7 + i + 4 + 18] == -1) {
                        advantage -= 2000;
                    }
                }
            }
        }
        return advantage;
    }

    private ArrayList<Integer> legalMoves() {
        ArrayList<Integer>  outArr = new ArrayList<>(0);
        for (int i = 1; i <= 7; i++) {
            if (playingBoard[i - 1] == 0) {
                outArr.add(i);
            }
        }
        return outArr;
    }

}
