import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Player {

    // Type 0 = Human
    // Type 1 = Random
    // Type 2 = Minimax
    // Type 3 = Monte Carlo Tree Search
    private final int playerType;
    private final int depth;

    public Player(int playerType, int depth) {
        this.playerType = playerType;
        this.depth = depth;
    }

    int counter;

    public int fetchMove() {
        counter = 0;
        int col1val = 69420;
        int col2val = 69420;
        int col3val = 69420;
        int col4val = 69420;
        int col5val = 69420;
        int col6val = 69420;
        int col7val = 69420;
        int winnerval;
        int loserval;
        if (Board.getInstance().getTurn()) {
            winnerval = 9999;
            loserval = -9999;
        } else {
            winnerval = -9999;
            loserval = 9999;
        }
        boolean turn = Board.getInstance().getTurn();
        if (Board.getInstance().isColNotFull(1)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(1, turn);
            col1val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("1:"+col1val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(2)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(2, turn);
            col2val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("2:"+col2val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(3)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(3, turn);
            col3val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("3:"+col3val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(4)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(4, turn);
            col4val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("4:"+col4val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(5)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(5, turn);
            col5val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("5:"+col5val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(6)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(6, turn);
            col6val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("6:"+col6val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(7)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(7, turn);
            col7val = search(depth, Board.getInstance().getTurn(), loserval, winnerval);
                System.out.println("7:"+col7val);
            Board.getInstance().loadBB();
            Timer.end();
        }
        int printval = 0;
        if (col1val != 69420 && col1val != 0) {
            printval = col1val;
        }
        if (col2val != 69420 && col2val != 0) {
            if (printval == 0) {
                printval = col2val;
            } else if (printval > 0) {
                if (col2val < 0) {
                    if (-col2val > printval) {
                        printval = col2val;
                    }
                } else {
                    if (col2val > printval) {
                        printval = col2val;
                    }
                }
            } else {
                if (col2val > 0) {
                    if (col2val > -printval) {
                        printval = col2val;
                    }
                } else {
                    if (col2val < printval) {
                        printval = col2val;
                    }
                }
            }
        }
        if (col3val != 69420 && col3val != 0) {
            if (printval == 0) {
                printval = col3val;
            } else if (printval > 0) {
                if (col3val < 0) {
                    if (-col3val > printval) {
                        printval = col3val;
                    }
                } else {
                    if (col3val > printval) {
                        printval = col3val;
                    }
                }
            } else {
                if (col3val > 0) {
                    if (col3val > -printval) {
                        printval = col3val;
                    }
                } else {
                    if (col3val < printval) {
                        printval = col3val;
                    }
                }
            }
        }
        if (col4val != 69420 && col4val != 0) {
            if (printval == 0) {
                printval = col4val;
            } else if (printval > 0) {
                if (col4val < 0) {
                    if (-col4val > printval) {
                        printval = col4val;
                    }
                } else {
                    if (col4val > printval) {
                        printval = col4val;
                    }
                }
            } else {
                if (col4val > 0) {
                    if (col4val > -printval) {
                        printval = col4val;
                    }
                } else {
                    if (col4val < printval) {
                        printval = col4val;
                    }
                }
            }
        }
        if (col5val != 69420 && col5val != 0) {
            if (printval == 0) {
                printval = col5val;
            } else if (printval > 0) {
                if (col5val < 0) {
                    if (-col5val > printval) {
                        printval = col5val;
                    }
                } else {
                    if (col5val > printval) {
                        printval = col5val;
                    }
                }
            } else {
                if (col5val > 0) {
                    if (col5val > -printval) {
                        printval = col5val;
                    }
                } else {
                    if (col5val < printval) {
                        printval = col5val;
                    }
                }
            }
        }
        if (col6val != 69420 && col6val != 0) {
            if (printval == 0) {
                printval = col6val;
            } else if (printval > 0) {
                if (col6val < 0) {
                    if (-col6val > printval) {
                        printval = col6val;
                    }
                } else {
                    if (col6val > printval) {
                        printval = col6val;
                    }
                }
            } else {
                if (col6val > 0) {
                    if (col6val > -printval) {
                        printval = col6val;
                    }
                } else {
                    if (col6val < printval) {
                        printval = col6val;
                    }
                }
            }
        }
        if (col7val != 69420 && col7val != 0) {
            if (printval == 0) {
                printval = col7val;
            } else if (printval > 0) {
                if (col7val < 0) {
                    if (-col7val > printval) {
                        printval = col7val;
                    }
                } else {
                    if (col7val > printval) {
                        printval = col7val;
                    }
                }
            } else {
                if (col7val > 0) {
                    if (col7val > -printval) {
                        printval = col7val;
                    }
                } else {
                    if (col7val < printval) {
                        printval = col7val;
                    }
                }
            }
        }
        System.out.println("Eval: " + printval);
        if (playerType == 0) { // Human
            int column;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("In which column do you want to place?");
                    // Number between 1 and 7
                    column = Integer.parseInt(scanner.next());
                    if (column < 1 || column > 7) {
                        throw new java.lang.NumberFormatException();
                    }
                    // Is the column free?
                    int[] playingBoard = Board.getInstance().getPlayingBoard();
                    for (int i = 5; i >= 0; i--) {
                        if (playingBoard[i * 6 + column - 1 + i] == 0) {
                            return column;
                        } else if (i == 0 && playingBoard[column - 1 + i] != 0) {
                            throw new java.lang.NumberFormatException();
                        }
                    }
                } catch (java.lang.NumberFormatException use) {
                    System.out.println("Not a correct input.");
                }

            }
        }
        else if (playerType == 1) { // Random
            while (true) {
                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 7 + 1);
                int[] playingBoard = Board.getInstance().getPlayingBoard();
                for (int i = 5; i >= 0; i--) {
                    if (playingBoard[i * 6 + column - 1 + i] == 0) {
                        return column;
                    }
                }
            }
        }
        else if (playerType == 2) { // Minimax
            int better = 0;
            System.out.println("Winner Play Check");
            if (col1val == winnerval) {
                return 1;
            } else if (col2val == winnerval) {
                return 2;
            } else if (col3val == winnerval) {
                return 3;
            } else if (col4val == winnerval) {
                return 4;
            } else if (col5val == winnerval) {
                return 5;
            }  else if (col6val == winnerval) {
                return 6;
            } else if (col7val == winnerval) {
                return 7;
            }
            System.out.println("Better Play Check");
            int[] bettercolarr = new int[7];
            if (Board.getInstance().getTurn()) {
                if (col1val > better && col1val != 69420) {
                    better = col1val;
                    bettercolarr[0] = 1;
                }
                if (col2val > better && col2val != 69420) {
                    better = col2val;
                    bettercolarr[1] = 2;
                }
                if (col3val > better && col3val != 69420) {
                    better = col2val;
                    bettercolarr[2] = 3;
                }
                if (col4val > better && col4val != 69420) {
                    better = col4val;
                    bettercolarr[3] = 4;
                }
                if (col5val > better && col5val != 69420) {
                    better = col5val;
                    bettercolarr[4] = 5;
                }
                if (col6val > better && col6val != 69420) {
                    better = col6val;
                    bettercolarr[5] = 6;
                }
                if (col7val > better && col7val != 69420) {
                    bettercolarr[6] = 7;
                }
            } else {
                if (col1val < better) {
                    better = col1val;
                    bettercolarr[0] = 1;
                }
                if (col2val < better) {
                    better = col2val;
                    bettercolarr[1] = 2;
                }
                if (col3val < better) {
                    better = col2val;
                    bettercolarr[2] = 3;
                }
                if (col4val < better) {
                    better = col4val;
                    bettercolarr[3] = 4;
                }
                if (col5val < better) {
                    better = col5val;
                    bettercolarr[4] = 5;
                }
                if (col6val < better) {
                    better = col6val;
                    bettercolarr[5] = 6;
                }
                if (col7val < better) {
                    bettercolarr[6] = 7;
                }
            }
            boolean contains = false;
            for (int c: bettercolarr) {
                if (c != 0) {
                    contains = true;
                    break;
                }
            }
            if (contains) {
                System.out.println(Arrays.toString(bettercolarr));
//                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 7);
//                return bettercolarr.get(column);
//                return bettercolarr.get(bettercolarr.size() - 1);
                for (int i = 6; i >= 0; i--) {
                    if (bettercolarr[i] != 0) {
                        return i + 1;
                    }
                }
            }
            System.out.println("Draw Play Check");
            ArrayList<Integer> cold = new ArrayList<>(0);
            if (col1val == 0) {
                cold.add(1);
            }
            if (col2val == 0) {
                cold.add(2);
            }
            if (col3val == 0) {
                cold.add(3);
            }
            if (col4val == 0) {
                cold.add(4);
            }
            if (col5val == 0) {
                cold.add(5);
            }
            if (col6val == 0) {
                cold.add(6);
            }
            if (col7val == 0) {
                cold.add(7);
            }
            if (cold.size() > 0) {
                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, cold.size());
                return cold.get(column);
            }
            System.out.println("Worse Play Check");
            int worse = loserval;
            int[] worsecolarr = new int[7];
            if (Board.getInstance().getTurn()) {
                if (col1val != loserval && col1val != 69420) {
                    if (col1val >= worse) {
                        worse = col1val;
                        worsecolarr[0] = 1;
                    }
                }
                if (col2val != loserval && col2val != 69420) {
                    if (col2val >= worse) {
                        worse = col2val;
                        worsecolarr[1] = 2;
                    }
                }
                if (col3val != loserval && col3val != 69420) {
                    if (col3val >= worse) {
                        worse = col3val;
                        worsecolarr[2] = 3;
                    }
                }
                if (col4val != loserval && col4val != 69420) {
                    if (col4val >= worse) {
                        worse = col4val;
                        worsecolarr[3] = 4;
                    }
                }
                if (col5val != loserval && col5val != 69420) {
                    if (col5val >= worse) {
                        worse = col5val;
                        worsecolarr[4] = 5;
                    }
                }
                if (col6val != loserval && col6val != 69420) {
                    if (col6val >= worse) {
                        worse = col6val;
                        worsecolarr[5] = 6;
                    }
                }
                if (col7val != loserval && col7val != 69420) {
                    if (col7val >= worse) {
                        worsecolarr[6] = 7;
                    }
                }
            } else {
                if (col1val != loserval && col1val != 69420) {
                    if (col1val <= worse) {
                        worse = col1val;
                        worsecolarr[0] = 1;
                    }
                }
                if (col2val != loserval && col2val != 69420) {
                    if (col2val <= worse) {
                        worse = col2val;
                        worsecolarr[1] = 2;
                    }
                }
                if (col3val != loserval && col3val != 69420) {
                    if (col3val <= worse) {
                        worse = col3val;
                        worsecolarr[2] = 3;
                    }
                }
                if (col4val != loserval && col4val != 69420) {
                    if (col4val <= worse) {
                        worse = col4val;
                        worsecolarr[3] = 4;
                    }
                }
                if (col5val != loserval && col5val != 69420) {
                    if (col5val <= worse) {
                        worse = col5val;
                        worsecolarr[4] = 5;
                    }
                }
                if (col6val != loserval && col6val != 69420) {
                    if (col6val <= worse) {
                        worse = col6val;
                        worsecolarr[5] = 6;
                    }
                }
                if (col7val != loserval && col7val != 69420) {
                    if (col7val <= worse) {
                        worsecolarr[6] = 7;
                    }
                }
            }
            contains = false;
            for (int c: worsecolarr) {
                if (c != 0) {
                    contains = true;
                    break;
                }
            }
            if (contains) {
                System.out.println(Arrays.toString(worsecolarr));
//                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 7);
//                return worsecolarr.get(column);
//                return worsecolarr.get(worsecolarr.size() - 1);
                for (int i = 6; i >= 0; i--) {
                    if (worsecolarr[i] != 0) {
                        return i + 1;
                    }
                }
            }
            System.out.println("Random Play");
            while (true) {
                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 7 + 1);
                int[] playingBoard = Board.getInstance().getPlayingBoard();
                for (int i = 5; i >= 0; i--) {
                    if (playingBoard[i * 6 + column - 1 + i] == 0) {
                        return column;
                    }
                }
            }
        }
        return -1;
    }

    private int search(int depth, boolean maximizingPlayer, int alpha, int beta) { // Move-ordering, transposition table
        counter++;
        int evl = Board.getInstance().evaluate();
        if (depth == 0 || evl == 9999 || evl == -9999) {
//            return Board.getInstance().evaluate() / (this.depth - depth + 1);
//            if ((Board.getInstance().evaluate() == 9999 || Board.getInstance().evaluate() == -9999) && depth != 0) {
//                System.out.println("c: " + counter + " | depth: " + depth + " | mP: " + maximizingPlayer + " | a: " + alpha + " | b: " + beta);
//            }
            return evl;
        }

        ArrayList<Integer> moves = Board.getInstance().legalMoves();
//        order(moves);
        if (maximizingPlayer) {
            int maxEval = -9999;
            for (int m: moves) {
                Board.getInstance().makeMove(m, true);
                int eval = search(depth - 1, false, alpha, beta);
                Board.getInstance().undoMove(m);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
//                if (beta <= alpha) {
//                    break;
//                }
            }
            return maxEval;
        } else {
            int minEval = 9999;
            for (int m: moves) {
                Board.getInstance().makeMove(m, false);
                int eval = search(depth - 1, true, alpha, beta);
                Board.getInstance().undoMove(m);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
//                if (beta <= alpha) {
//                    break;
//                }
            }
            return minEval;
        }
    }

    private void order(int[] moves) {
        int[] scores = new int[7];
        for (int i = 0; i < 7; i++) {
            int score = 0;
            // Find a way to rate the move
            // Maybe eval func with this move filled
            scores[i] = score;
        }

        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j > 0; j--) {
                int swapIndex = j - 1;
                if (scores[swapIndex] < scores[j]) {
                    int c;
                    c = moves[j];
                    moves[j] = moves[swapIndex];
                    moves[swapIndex] = c;
                    c = scores[j];
                    scores[j] = scores[swapIndex];
                    scores[swapIndex] = c;
                }
            }
        }
    }

}
