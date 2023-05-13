import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private static volatile Board instance;
    private int[] playingBoard;
    protected static boolean wasInput = false;

    private Board() {
        this.playingBoard = new int[42];
    }

    public static Board getInstance() {
        Board result = instance;
        if (result == null) {
            synchronized (Board.class) {
                result = instance;
                if (result == null) {
                    instance = new Board();
                }
            }
        }
        return instance;
    }

    public int[] getPlayingBoard() {
        return playingBoard;
    }

    public void printBoard() {
        Window.frame.repaint();
        System.out.println("~~~~~~~~~~\nPosition-ID: " + new HashRep().decode(playingBoard));
        System.out.println("Position: " + Arrays.toString(playingBoard));
        int start = 0;
        int stop = 7;
        for (int i = 0; i < 6; i++) {
            for (int j = start; j < stop; j++) {
                if (j !=  start) {
                    System.out.print(" ");
                }
                if (playingBoard[i * 6 + j] == 0) {
                    System.out.print("-");
                } else if (playingBoard[i * 6 + j] == 1) {
                    System.out.print("X");
                } else if (playingBoard[i * 6 + j] == -1) {
                    System.out.print("O");
                }
            }
            System.out.print("\n");
            start++;
            stop++;
        }
    }

    private boolean turn = true;

    public void gameLoop(int playerW, int playerB, int[] board, boolean print, int depth, boolean waiter) {
        playingBoard = board;
        Player playerWhite;
        Player playerBlack;
        if (playerW == 1) {
            playerWhite = new PlayerRandom();
        } else if (playerW == 2) {
            playerWhite = new PlayerMinimax(depth);
        } else {
            playerWhite = new PlayerHuman();
        }
        if (playerB == 1) {
            playerBlack = new PlayerRandom();
        } else if (playerB == 2) {
            playerBlack = new PlayerMinimax(depth);
        } else {
            playerBlack = new PlayerHuman();
        }
        while (areFieldsLeft() && evaluate() != 999999 && evaluate() != -999999) {
            if (print) printBoard();
            if (waiter) {
                long startTime = System.nanoTime();
                long endTime;
                do {
                    endTime = System.nanoTime();
                } while (Math.round((endTime - startTime) / 1e+6) < 100);
            }
            if (turn) {
                if (print) System.out.println("?> White Starts Thinking");
                if (!playerWhite.getClass().equals(PlayerHuman.class)) {
                    makeMove(playerWhite.fetchMove(print), turn);
                } else {
                    wasInput = false;
                    while (!wasInput) {
                        Thread.onSpinWait();
                    }
                }
                if (print) System.out.println("?> White made a move.");
                turn = false;
            } else {
                if (print) System.out.println("?> Black Starts Thinking");
                if (!playerBlack.getClass().equals(PlayerHuman.class)) {
                    makeMove(playerBlack.fetchMove(print), turn);
                } else {
                    wasInput = false;
                    while (!wasInput) {
                        Thread.onSpinWait();
                    }
                }
                if (print) System.out.println("?> Black made a move.");
                turn = true;
            }
            if (evaluate() == 999999 || evaluate() == -999999) {
                break;
            }
        }
        if (print) printBoard();
        if (evaluate() == 999999) {
            if (print) System.out.println("\nWhite won.");
        } else if (evaluate() == -999999) {
            if (print) System.out.println("\nBlack won.");
        } else {
            if (print) System.out.println("\nDraw.");
        }
        if (print) System.out.println("Average ms time: " + Timer.averageMs());
    }

    protected void makeMove(int column, boolean turn) {
        int symbol;
        for (int i = 5; i >= 0; i--) {
            if (playingBoard[i * 6 + column - 1 + i] == 0) {
                if (turn) {
                    symbol = 1;
                } else {
                    symbol = -1;
                }
                playingBoard[i * 6 + column - 1 + i] = symbol;
                break;
            }
        }
    }

    protected void undoMove(int column) {
        for (int i = 0; i < 6; i++) {
            if (playingBoard[i * 6 + column - 1 + i] != 0) {
                playingBoard[i * 6 + column - 1 + i] = 0;
                break;
            }
        }
    }

    protected int evaluate() {
        return evaluate(playingBoard);
    }

    protected int evaluate(int[] position) {
        // winner eval
        final int winnerBlack =  -999999;
        final int winnerWhite =   999999;
        final int bestAdvantage = 500000; // 500000
        final int goodAdvantage = 250000; // 250000
        final int okAdvantage =   10000; // 10000
        int advantage = 0;
        // Check for horizontal winner & forces
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (position[i * 6 + j + i] == 0) {
                    continue;
                }
                switch (position[i * 6 + j + i] + position[i * 6 + j + 1 + i] + position[i * 6 + j + 2 + i] + position[i * 6 + j + 3 + i]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
            }
        }
        // Check for vertical winner & forces
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                if (position[i + j * 7] == 0) {
                    continue;
                }
                switch (position[i + j * 7] + position[i + j * 7 + 7] + position[i + j * 7 + 14] + position[i + j * 7 + 21]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
            }
        }
        // Check for diagonal winner & forces
        // -Left Top Right Bottom
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (position[j * 7 + i] == 0) {
                    continue;
                }
                switch (position[j * 7 + i] + position[j * 7 + i + 8] + position[j * 7 + i + 16] + position[j * 7 + i + 24]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
            }
        }
        // -Left Bottom Right Top
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (position[j * 7 + i + 3] == 0) {
                    continue;
                }
                switch (position[j * 7 + i + 3] + position[j * 7 + i + 3 + 6] + position[j * 7 + i + 3 + 12] + position[j * 7 + i + 3 + 18]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
            }
        }
        // Traps [-xxx-] & Pre-traps [-x-x-]
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                if (position[i * 6 + j + i] == 0 && position[i * 6 + j + 4 + i] == 0) {
                    if (position[i * 6 + j + 1 + i] + position[i * 6 + j + 2 + i] + position[i * 6 + j + 3 + i] == 3) {
                        advantage += bestAdvantage;
                    } else if (position[i * 6 + j + 1 + i] + position[i * 6 + j + 2 + i] + position[i * 6 + j + 3 + i] == -3) {
                        advantage -= bestAdvantage;
                    } else if (position[i * 6 + j + 1 + i] == 1 && position[i * 6 + j + 2 + i] == 1 && position[i * 6 + j + 3 + i] == 0) {
                        advantage += goodAdvantage;
                    } else if (position[i * 6 + j + 1 + i] == 1 && position[i * 6 + j + 2 + i] == 0 && position[i * 6 + j + 3 + i] == 1) {
                        advantage += goodAdvantage;
                    } else if (position[i * 6 + j + 1 + i] == 0 && position[i * 6 + j + 2 + i] == 1 && position[i * 6 + j + 3 + i] == 1) {
                        advantage += goodAdvantage;
                    } else if (position[i * 6 + j + 1 + i] == -1 && position[i * 6 + j + 2 + i] == -1 && position[i * 6 + j + 3 + i] == 0) {
                        advantage -= goodAdvantage;
                    } else if (position[i * 6 + j + 1 + i] == -1 && position[i * 6 + j + 2 + i] == 0 && position[i * 6 + j + 3 + i] == -1) {
                        advantage -= goodAdvantage;
                    } else if (position[i * 6 + j + 1 + i] == 0 && position[i * 6 + j + 2 + i] == -1 && position[i * 6 + j + 3 + i] == -1) {
                        advantage -= goodAdvantage;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (position[j * 7 + i] == 0 && position[j * 7 + i + 32] == 0) {
                    if (position[j * 7 + i + 8] + position[j * 7 + i + 16] + position[j * 7 + i + 24] == 3) {
                        advantage += bestAdvantage;
                    } else if (position[j * 7 + i + 8] + position[j * 7 + i + 16] + position[j * 7 + i + 24] == -3) {
                        advantage -= bestAdvantage;
                    } else if (position[j * 7 + i + 8] == 1 && position[j * 7 + i + 16] == 1 && position[j * 7 + i + 24] == 0) {
                        advantage += goodAdvantage;
                    } else if (position[j * 7 + i + 8] == 1 && position[j * 7 + i + 16] == 0 && position[j * 7 + i + 24] == 1) {
                        advantage += goodAdvantage;
                    } else if (position[j * 7 + i + 8] == 0 && position[j * 7 + i + 16] == 1 && position[j * 7 + i + 24] == 1) {
                        advantage += goodAdvantage;
                    } else if (position[j * 7 + i + 8] == -1 && position[j * 7 + i + 16] == -1 && position[j * 7 + i + 24] == 0) {
                        advantage -= goodAdvantage;
                    } else if (position[j * 7 + i + 8] == -1 && position[j * 7 + i + 16] == 0 && position[j * 7 + i + 24] == -1) {
                        advantage -= goodAdvantage;
                    } else if (position[j * 7 + i + 8] == 0 && position[j * 7 + i + 16] == -1 && position[j * 7 + i + 24] == -1) {
                        advantage -= goodAdvantage;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (position[j * 7 + i + 4] == 0 && position[j * 7 + i + 4 + 24] == 0) {
                    if (position[j * 7 + i + 4 + 6] + position[j * 7 + i + 4 + 12] + position[j * 7 + i + 4 + 18] == 3) {
                        advantage += bestAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] + position[j * 7 + i + 4 + 12] + position[j * 7 + i + 4 + 18] == -3) {
                        advantage -= bestAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] == 1 && position[j * 7 + i + 4 + 12] == 1 && position[j * 7 + i + 4 + 18] == 0) {
                        advantage += goodAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] == 1 && position[j * 7 + i + 4 + 12] == 0 && position[j * 7 + i + 4 + 18] == 1) {
                        advantage += goodAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] == 0 && position[j * 7 + i + 4 + 12] == 1 && position[j * 7 + i + 4 + 18] == 1) {
                        advantage += goodAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] == -1 && position[j * 7 + i + 4 + 12] == -1 && position[j * 7 + i + 4 + 18] == 0) {
                        advantage -= goodAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] == -1 && position[j * 7 + i + 4 + 12] == 0 && position[j * 7 + i + 4 + 18] == -1) {
                        advantage -= goodAdvantage;
                    } else if (position[j * 7 + i + 4 + 6] == 0 && position[j * 7 + i + 4 + 12] == -1 && position[j * 7 + i + 4 + 18] == -1) {
                        advantage -= goodAdvantage;
                    }
                }
            }
        }
        // Special Patterns
        // -Pre-seven [xx][?x][x?]
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (position[i * 6 + j + i] == 0) {
                    continue;
                }
                if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 8] + position[i * 6 + j + i + 14] == 4) {
                    advantage += goodAdvantage;
                } else if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 8] + position[i * 6 + j + i + 14] == -4) {
                    advantage -= goodAdvantage;
                } else if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 7] + position[i * 6 + j + i + 15] == 4) {
                    advantage += goodAdvantage;
                } else if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 7] + position[i * 6 + j + i + 15] == -4) {
                    advantage -= goodAdvantage;
                }
            }
        }
        return advantage;
    }

    protected boolean areFieldsLeft() {
        for (int i = 0; i < 42; i++) {
            if (playingBoard[i] == 0) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<Integer> legalMoves() {
        ArrayList<Integer>  outArr = new ArrayList<>(0);
        for (int i = 1; i <= 7; i++) {
            if (playingBoard[i - 1] == 0) {
                outArr.add(i);
            }
        }
        return outArr;
    }

    private final int[] backupBoard = new int[42];
    private boolean backupTurn;
    protected void createBB() {
        System.arraycopy(playingBoard, 0, backupBoard, 0, 42);
        backupTurn = turn;
    }

    protected void loadBB() {
        System.arraycopy(backupBoard, 0, playingBoard, 0, 42);
        turn = backupTurn;
    }

    protected int[] fetchBB() {
        return backupBoard;
    }

    public boolean getTurn() {
        return turn;
    }

    public boolean isColNotFull(int col) {
        return playingBoard[col - 1] == 0;
    }

    public void loadPosition(int[] position) {
        playingBoard = position;
    }

    public int winnerAfterMove(boolean whoWillMakeTheMoveNow, int winnerval, int loserval) {
        for (int i = 1; i <= 7; i++) {
            if (isColNotFull(i)) {
                makeMove(i, whoWillMakeTheMoveNow);
                int eval = evaluate();
                undoMove(i);
                if (eval == winnerval) {
                    return  winnerval;
                } else if (eval == loserval) {
                    return loserval;
                }
            }
        }
        return 0;
    }

}
