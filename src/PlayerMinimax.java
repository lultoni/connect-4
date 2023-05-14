import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PlayerMinimax extends Player {
    HashRep transpositionTable = new HashRep();
    int alpha = -999999;
    int beta = 999999;

    public PlayerMinimax(int depth) {
        super(depth);
    }

    protected void assessCols(boolean turn, int winnerval, int loserval, ArrayList<Integer> evalList, ArrayList<Integer> psList, boolean print) {
        long startTime = System.nanoTime();
        transpositionTable.clear();
        alpha = -999999;
        beta = 999999;
        int localAlpha = alpha;
        int localBeta = beta;
        if (Board.getInstance().isColNotFull(1)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(1, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col1val = loserval;
            } else if (comp == winnerval) {
                col1val = winnerval;
            } else {
                col1val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("1: " + col1val);
            evalList.add(col1val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
//        transpositionTable.clear();
//        if (turn) localAlpha = Math.max(localAlpha, col1val);
//        if (!turn) localBeta = Math.min(localBeta, col1val);
//        System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
        if (Board.getInstance().isColNotFull(2)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(2, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col2val = loserval;
            } else if (comp == winnerval) {
                col2val = winnerval;
            } else {
                col2val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("2: " + col2val);
            evalList.add(col2val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
//        transpositionTable.clear();
//        if (turn) localAlpha = Math.max(localAlpha, col2val);
//        if (!turn) localBeta = Math.min(localBeta, col2val);
//        System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
        if (Board.getInstance().isColNotFull(3)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(3, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col3val = loserval;
            } else if (comp == winnerval) {
                col3val = winnerval;
            } else {
                col3val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("3: " + col3val);
            evalList.add(col3val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
//        transpositionTable.clear();
//        if (turn) localAlpha = Math.max(localAlpha, col3val);
//        if (!turn) localBeta = Math.min(localBeta, col3val);
//        System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
        if (Board.getInstance().isColNotFull(4)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(4, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col4val = loserval;
            } else if (comp == winnerval) {
                col4val = winnerval;
            } else {
                col4val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("4: " + col4val);
            evalList.add(col4val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
//        transpositionTable.clear();
//        if (turn) localAlpha = Math.max(localAlpha, col4val);
//        if (!turn) localBeta = Math.min(localBeta, col4val);
//        System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
        if (Board.getInstance().isColNotFull(5)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(5, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col5val = loserval;
            } else if (comp == winnerval) {
                col5val = winnerval;
            } else {
                col5val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("5: " + col5val);
            evalList.add(col5val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
//        transpositionTable.clear();
//        if (turn) localAlpha = Math.max(localAlpha, col5val);
//        if (!turn) localBeta = Math.min(localBeta, col5val);
//        System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
        if (Board.getInstance().isColNotFull(6)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(6, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col6val = loserval;
            } else if (comp == winnerval) {
                col6val = winnerval;
            } else {
                col6val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("6: " + col6val);
            Board.getInstance().loadBB();
            Timer.end();
        }
//        transpositionTable.clear();
//        if (turn) localAlpha = Math.max(localAlpha, col6val);
//        if (!turn) localBeta = Math.min(localBeta, col6val);
//        System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
        if (Board.getInstance().isColNotFull(7)) {
            Timer.start();
            Board.getInstance().createBB();
            Board.getInstance().makeMove(7, turn);
            counter = 0;
            int comp = Board.getInstance().winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) {
                col7val = loserval;
            } else if (comp == winnerval) {
                col7val = winnerval;
            } else {
                col7val = search(depth, !turn, localAlpha, localBeta);
            }
            if (print) System.out.println("7: " + col7val);
//            if (turn) localAlpha = Math.max(localAlpha, col7val);
//            if (!turn) localBeta = Math.min(localBeta, col7val);
//            System.out.println("a[" + localAlpha + "] b[" + localBeta + "]");
            evalList.add(col7val);
            psList.add(counter);
            Board.getInstance().loadBB();
            Timer.end();
        }
        long endTime = System.nanoTime();
        long outTime = Math.round((endTime - startTime) / 1e+6);
        if (print) System.out.println("No Multithreading: " + outTime + " ms");
    }

    @Override
    public int fetchMove(boolean print) {
        if (print) System.out.println("Positions in the HashTable: " + transpositionTable.size());
        counter = 0;
        int winnerval;
        int loserval;
        if (Board.getInstance().getTurn()) {
            winnerval = 999999;
            loserval = -999999;
        } else {
            winnerval = -999999;
            loserval = 999999;
        }
        boolean turn = Board.getInstance().getTurn();
        ArrayList<Integer> evalList = new ArrayList<>(0);
        ArrayList<Integer> psList = new ArrayList<>(0);
        if (Arrays.equals(Board.getInstance().getPlayingBoard(), new int[42])) return 4;
        if (Arrays.equals(Board.getInstance().getPlayingBoard(), new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0})) return 4;
        assessCols(turn, winnerval, loserval, evalList, psList, print);
        if (print) System.out.println("EL: " + evalList);
        if (print) System.out.println("PSL: " + psList);
        int better = 0;
        if (print) System.out.println("Winner Play Check");
        if (col1val == winnerval && Board.getInstance().isColNotFull(1)) {
            return 1;
        } else if (col2val == winnerval && Board.getInstance().isColNotFull(2)) {
            return 2;
        } else if (col3val == winnerval && Board.getInstance().isColNotFull(3)) {
            return 3;
        } else if (col4val == winnerval && Board.getInstance().isColNotFull(4)) {
            return 4;
        } else if (col5val == winnerval && Board.getInstance().isColNotFull(5)) {
            return 5;
        } else if (col6val == winnerval && Board.getInstance().isColNotFull(6)) {
            return 6;
        } else if (col7val == winnerval && Board.getInstance().isColNotFull(7)) {
            return 7;
        }
        if (print) System.out.println("Better Play Check");
        int[] bettercolarr = new int[7];
        if (Board.getInstance().getTurn()) {
            if (col1val != 0 && col1val != 69420 && Board.getInstance().isColNotFull(1)) {
                if (col1val > better) {
                    better = col1val;
                    bettercolarr[0] = 1;
                }
            }
            if (col2val != 0 && col2val != 69420 && Board.getInstance().isColNotFull(2)) {
                if (col2val > better) {
                    better = col2val;
                    bettercolarr[0] = 0;
                    bettercolarr[1] = 2;
                } else if (col2val == better) {
                    bettercolarr[1] = 2;
                }
            }
            if (col3val != 0 && col3val != 69420 && Board.getInstance().isColNotFull(3)) {
                if (col3val > better) {
                    better = col3val;
                    bettercolarr[0] = 0;
                    bettercolarr[1] = 0;
                    bettercolarr[2] = 3;
                } else if (col3val == better) {
                    bettercolarr[2] = 3;
                }
            }
            if (col4val != 0 && col4val != 69420 && Board.getInstance().isColNotFull(4)) {
                if (col4val > better) {
                    better = col4val;
                    bettercolarr[0] = 0;
                    bettercolarr[1] = 0;
                    bettercolarr[2] = 0;
                    bettercolarr[3] = 4;
                } else if (col4val == better) {
                    bettercolarr[3] = 4;
                }
            }
            if (col5val != 0 && col5val != 69420 && Board.getInstance().isColNotFull(5)) {
                if (col5val > better) {
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
            if (col6val != 0 && col6val != 69420 && Board.getInstance().isColNotFull(6)) {
                if (col6val > better) {
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
            if (col7val != 0 && col7val != 69420 && Board.getInstance().isColNotFull(7)) {
                if (col7val > better) {
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
            if (col1val != 0 && col1val != 69420 && Board.getInstance().isColNotFull(1)) {
                if (col1val < better) {
                    better = col1val;
                    bettercolarr[0] = 1;
                }
            }
            if (col2val != 0 && col2val != 69420 && Board.getInstance().isColNotFull(2)) {
                if (col2val < better) {
                    better = col2val;
                    bettercolarr[0] = 0;
                    bettercolarr[1] = 2;
                } else if (col2val == better) {
                    bettercolarr[1] = 2;
                }
            }
            if (col3val != 0 && col3val != 69420 && Board.getInstance().isColNotFull(3)) {
                if (col3val < better) {
                    better = col3val;
                    bettercolarr[0] = 0;
                    bettercolarr[1] = 0;
                    bettercolarr[2] = 3;
                } else if (col3val == better) {
                    bettercolarr[2] = 3;
                }
            }
            if (col4val != 0 && col4val != 69420 && Board.getInstance().isColNotFull(4)) {
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
            if (col5val != 0 && col5val != 69420 && Board.getInstance().isColNotFull(5)) {
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
            if (col6val != 0 && col6val != 69420 && Board.getInstance().isColNotFull(6)) {
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
            if (col7val != 0 && col7val != 69420 && Board.getInstance().isColNotFull(7)) {
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
            if (print) System.out.println(Arrays.toString(bettercolarr));
            for (int m: bettercolarr) {
                if (m != 0) {
                    return m;
                }
            }
            while (true) {
                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 7);
                if (bettercolarr[column] != 0) {
                    return column + 1;
                }
            }
        }
        if (print) System.out.println("Draw Play Check");
        ArrayList<Integer> cold = new ArrayList<>(0);
        if (col1val == 0 && Board.getInstance().isColNotFull(1)) {
            cold.add(1);
        }
        if (col2val == 0 && Board.getInstance().isColNotFull(2)) {
            cold.add(2);
        }
        if (col3val == 0 && Board.getInstance().isColNotFull(3)) {
            cold.add(3);
        }
        if (col4val == 0 && Board.getInstance().isColNotFull(4)) {
            cold.add(4);
        }
        if (col5val == 0 && Board.getInstance().isColNotFull(5)) {
            cold.add(5);
        }
        if (col6val == 0 && Board.getInstance().isColNotFull(6)) {
            cold.add(6);
        }
        if (col7val == 0 && Board.getInstance().isColNotFull(7)) {
            cold.add(7);
        }
        if (cold.size() > 0) {
            int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, cold.size());
            return cold.get(column);
        }
        if (print) System.out.println("Worse Play Check");
        int worse = loserval;
        int[] worsecolarr = new int[7];
        if (loserval == -999999) {
            if (col1val != loserval && col1val != 69420 && Board.getInstance().isColNotFull(1)) {
                if (col1val >= worse) {
                    worse = col1val;
                    worsecolarr[0] = 1;
                }
            }
            if (col2val != loserval && col2val != 69420 && Board.getInstance().isColNotFull(2)) {
                if (col2val > worse) {
                    worse = col2val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 2;
                } else if (col2val == worse) {
                    worsecolarr[1] = 2;
                }
            }
            if (col3val != loserval && col3val != 69420 && Board.getInstance().isColNotFull(3)) {
                if (col3val > worse) {
                    worse = col3val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 3;
                } else if (col3val == worse) {
                    worsecolarr[2] = 3;
                }
            }
            if (col4val != loserval && col4val != 69420 && Board.getInstance().isColNotFull(4)) {
                if (col4val > worse) {
                    worse = col4val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 4;
                } else if (col4val == worse) {
                    worsecolarr[3] = 4;
                }
            }
            if (col5val != loserval && col5val != 69420 && Board.getInstance().isColNotFull(5)) {
                if (col5val > worse) {
                    worse = col5val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 0;
                    worsecolarr[4] = 5;
                } else if (col5val == worse) {
                    worsecolarr[4] = 5;
                }
            }
            if (col6val != loserval && col6val != 69420 && Board.getInstance().isColNotFull(6)) {
                if (col6val > worse) {
                    worse = col6val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 0;
                    worsecolarr[4] = 0;
                    worsecolarr[5] = 6;
                } else if (col6val == worse) {
                    worsecolarr[5] = 6;
                }
            }
            if (col7val != loserval && col7val != 69420 && Board.getInstance().isColNotFull(7)) {
                if (col7val > worse) {
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 0;
                    worsecolarr[4] = 0;
                    worsecolarr[5] = 0;
                    worsecolarr[6] = 7;
                } else if (col7val == worse) {
                    worsecolarr[6] = 7;
                }
            }
        } else {
            if (col1val != loserval && col1val != 69420 && Board.getInstance().isColNotFull(1)) {
                if (col1val <= worse) {
                    worse = col1val;
                    worsecolarr[0] = 1;
                }
            }
            if (col2val != loserval && col2val != 69420 && Board.getInstance().isColNotFull(2)) {
                if (col2val < worse) {
                    worse = col2val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 2;
                } else if (col2val == worse) {
                    worsecolarr[1] = 2;
                }
            }
            if (col3val != loserval && col3val != 69420 && Board.getInstance().isColNotFull(3)) {
                if (col3val < worse) {
                    worse = col3val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 3;
                } else if (col3val == worse) {
                    worsecolarr[2] = 3;
                }
            }
            if (col4val != loserval && col4val != 69420 && Board.getInstance().isColNotFull(4)) {
                if (col4val < worse) {
                    worse = col4val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 4;
                } else if (col4val == worse) {
                    worsecolarr[3] = 4;
                }
            }
            if (col5val != loserval && col5val != 69420 && Board.getInstance().isColNotFull(5)) {
                if (col5val < worse) {
                    worse = col5val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 0;
                    worsecolarr[4] = 5;
                } else if (col5val == worse) {
                    worsecolarr[4] = 5;
                }
            }
            if (col6val != loserval && col6val != 69420 && Board.getInstance().isColNotFull(6)) {
                if (col6val < worse) {
                    worse = col6val;
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 0;
                    worsecolarr[4] = 0;
                    worsecolarr[5] = 6;
                } else if (col6val == worse) {
                    worsecolarr[5] = 6;
                }
            }
            if (col7val != loserval && col7val != 69420 && Board.getInstance().isColNotFull(7)) {
                if (col7val < worse) {
                    worsecolarr[0] = 0;
                    worsecolarr[1] = 0;
                    worsecolarr[2] = 0;
                    worsecolarr[3] = 0;
                    worsecolarr[4] = 0;
                    worsecolarr[5] = 0;
                    worsecolarr[6] = 7;
                } else if (col7val == worse) {
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
            if (print) System.out.println(Arrays.toString(worsecolarr));
//                int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 7);
//                return worsecolarr.get(column);
//                return worsecolarr.get(worsecolarr.size() - 1);
            for (int i = 6; i >= 0; i--) {
                if (worsecolarr[i] != 0) {
                    return i + 1;
                }
            }
        }
        if (print) System.out.println("Random Play");
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

    private int search(int depth, boolean maximizingPlayer, int alpha, int beta) {
        counter++;
        if (!Board.getInstance().areFieldsLeft(Board.getInstance().getPlayingBoard())) {
            return 0;
        }
        int evl = Board.getInstance().evaluate();
        if (depth == 0 || evl == 999999 || evl == -999999) {
            return evl / (this.depth - depth + 1);
        }
        int preS = transpositionTable.prevSearch(Board.getInstance().getPlayingBoard());
        if (preS != 69420) {
//            return preS;
        }

        ArrayList<Integer> moves = Board.getInstance().legalMoves();
        if (maximizingPlayer) {
            int maxEval = -999999;
            for (int m: moves) {
                Board.getInstance().makeMove(m, true);
                int eval = search(depth - 1, false, alpha, beta);
                Board.getInstance().undoMove(m);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
                this.alpha = alpha;
            }
            transpositionTable.add(Board.getInstance().getPlayingBoard(), maxEval);
            return maxEval;
        } else {
            int minEval = 999999;
            for (int m: moves) {
                Board.getInstance().makeMove(m, false);
                int eval = search(depth - 1, true, alpha, beta);
                Board.getInstance().undoMove(m);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
                this.beta = beta;
            }
            transpositionTable.add(Board.getInstance().getPlayingBoard(), minEval);
            return minEval;
        }
    }

}
