import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
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
            winnerval = 99999;
            loserval = -99999;
        } else {
            winnerval = -99999;
            loserval = 99999;
        }
        boolean turn = Board.getInstance().getTurn();
        ArrayList<Integer> evalList = new ArrayList<>(0);
        ArrayList<Integer> psList = new ArrayList<>(0);
        if (Board.getInstance().isColNotFull(1)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(1, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col1val = loserval;
            } else if (comp == winnerval) {
                col1val = winnerval;
            } else {
                col1val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col1val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(2)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(2, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col2val = loserval;
            } else if (comp == winnerval) {
                col2val = winnerval;
            } else {
                col2val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col2val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(3)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(3, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col3val = loserval;
            } else if (comp == winnerval) {
                col3val = winnerval;
            } else {
                col3val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col3val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(4)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(4, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col4val = loserval;
            } else if (comp == winnerval) {
                col4val = winnerval;
            } else {
                col4val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col4val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(5)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(5, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col5val = loserval;
            } else if (comp == winnerval) {
                col5val = winnerval;
            } else {
                col5val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col5val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(6)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(6, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col6val = loserval;
            } else if (comp == winnerval) {
                col6val = winnerval;
            } else {
                col6val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col6val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        if (Board.getInstance().isColNotFull(7)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(7, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(turn, winnerval, loserval);
            if (comp == loserval) {
                col7val = loserval;
            } else if (comp == winnerval) {
                col7val = winnerval;
            } else {
                col7val = search(depth, !turn, -99999, 99999);
            }
            evalList.add(col7val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        System.out.println(evalList);
        System.out.println(psList);
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
            if (Arrays.equals(Board.getInstance().getPlayingBoard(), new int[42])) {
                return 4;
            }
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
                if (col1val != 0) {
                    if (col1val > better && col1val != 69420) {
                        better = col1val;
                        bettercolarr[0] = 1;
                    }
                }
                if (col2val != 0) {
                    if (col2val > better && col2val != 69420) {
                        better = col2val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 2;
                    } else if (col2val == better) {
                        bettercolarr[1] = 2;
                    }
                }
                if (col3val != 0) {
                    if (col3val > better && col3val != 69420) {
                        better = col3val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 3;
                    } else if (col3val == better) {
                        bettercolarr[2] = 3;
                    }
                }
                if (col4val != 0) {
                    if (col4val > better && col4val != 69420) {
                        better = col4val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 4;
                    } else if (col4val == better) {
                        bettercolarr[3] = 4;
                    }
                }
                if (col5val != 0) {
                    if (col5val > better && col5val != 69420) {
                        better = col5val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 0;
                        bettercolarr[4] = 5;
                    } else if (col5val == better) {
                        bettercolarr[4] = 5;
                    }
                }
                if (col6val != 0) {
                    if (col6val > better && col6val != 69420) {
                        better = col6val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 0;
                        bettercolarr[4] = 0;
                        bettercolarr[5] = 6;
                    } else if (col6val == better) {
                        bettercolarr[5] = 6;
                    }
                }
                if (col7val != 0) {
                    if (col7val > better && col7val != 69420) {
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 0;
                        bettercolarr[4] = 0;
                        bettercolarr[5] = 0;
                        bettercolarr[6] = 7;
                    } else if (col7val == better) {
                        bettercolarr[6] = 7;
                    }
                }
            } else {
                if (col1val != 0) {
                    if (col1val < better) {
                        better = col1val;
                        bettercolarr[0] = 1;
                    }
                }
                if (col2val != 0) {
                    if (col2val < better) {
                        better = col2val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 2;
                    } else if (col2val == better) {
                        bettercolarr[1] = 2;
                    }
                }
                if (col3val != 0) {
                    if (col3val < better) {
                        better = col3val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 3;
                    } else if (col3val == better) {
                        bettercolarr[2] = 3;
                    }
                }
                if (col4val != 0) {
                    if (col4val < better) {
                        better = col4val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 4;
                    } else if (col4val == better) {
                        bettercolarr[3] = 4;
                    }
                }
                if (col5val != 0) {
                    if (col5val < better) {
                        better = col5val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 0;
                        bettercolarr[4] = 5;
                    } else if (col5val == better) {
                        bettercolarr[4] = 5;
                    }
                }
                if (col6val != 0) {
                    if (col6val < better) {
                        better = col6val;
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 0;
                        bettercolarr[4] = 0;
                        bettercolarr[5] = 6;
                    } else if (col6val == better) {
                        bettercolarr[5] = 6;
                    }
                }
                if (col7val != 0) {
                    if (col7val < better) {
                        bettercolarr[0] = 0;
                        bettercolarr[1] = 0;
                        bettercolarr[2] = 0;
                        bettercolarr[3] = 0;
                        bettercolarr[4] = 0;
                        bettercolarr[5] = 0;
                        bettercolarr[6] = 7;
                    } else if (col7val == better) {
                        bettercolarr[6] = 7;
                    }
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
                while (true) {
                    int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 7);
                    if (bettercolarr[column] != 0) {
                        return column + 1;
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

    private int search(int depth, boolean maximizingPlayer, int alpha, int beta) { // Transposition table
        counter++;
//        System.out.println("---------------\nc: " + counter + " | d: " + depth + " | mP: " + maximizingPlayer + " | a: " + alpha + " | b: " + beta);        Board.getInstance().printBoard();
        int evl = Board.getInstance().evaluate();
        if (depth == 0 || evl == 99999 || evl == -99999) {
            return evl / (this.depth - depth + 1);
//            return evl;
        }

        ArrayList<Integer> moves = Board.getInstance().legalMoves();
//        order(moves);
        if (maximizingPlayer) {
            int maxEval = -99999;
            for (int m: moves) {
                Board.getInstance().makeMove(m, true);
                int eval = search(depth - 1, false, alpha, beta);
                Board.getInstance().undoMove(m);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = 99999;
            for (int m: moves) {
                Board.getInstance().makeMove(m, false);
                int eval = search(depth - 1, true, alpha, beta);
                Board.getInstance().undoMove(m);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private void order(ArrayList<Integer> moves) {
        switch (moves.size()) {
            case 7 -> {
                int p1 = moves.get(0);
                int p2 = moves.get(1);
                int p3 = moves.get(2);
                int p4 = moves.get(3);
                int p5 = moves.get(4);
                int p6 = moves.get(5);
                int p7 = moves.get(6);
                moves.set(0, p4);
                moves.set(1, p3);
                moves.set(2, p5);
                moves.set(3, p2);
                moves.set(4, p6);
                moves.set(5, p1);
                moves.set(6, p7);
            }
            case 6 -> {
                int p1 = moves.get(0);
                int p2 = moves.get(1);
                int p3 = moves.get(2);
                int p4 = moves.get(3);
                int p5 = moves.get(4);
                int p6 = moves.get(5);
                moves.set(0, p4);
                moves.set(1, p3);
                moves.set(2, p5);
                moves.set(3, p2);
                moves.set(4, p6);
                moves.set(5, p1);
            }
            case 5 -> {
                int p1 = moves.get(0);
                int p2 = moves.get(1);
                int p3 = moves.get(2);
                int p4 = moves.get(3);
                int p5 = moves.get(4);
                moves.set(0, p3);
                moves.set(1, p4);
                moves.set(2, p2);
                moves.set(3, p5);
                moves.set(4, p1);
            }
            case 4 -> {
                int p1 = moves.get(0);
                int p2 = moves.get(1);
                int p3 = moves.get(2);
                int p4 = moves.get(3);
                moves.set(0, p2);
                moves.set(1, p3);
                moves.set(2, p4);
                moves.set(3, p1);
            }
            case 3 -> {
                int p1 = moves.get(0);
                int p2 = moves.get(1);
                int p3 = moves.get(2);
                moves.set(0, p2);
                moves.set(1, p3);
                moves.set(2, p1);
            }
        }
    }

}
