import java.util.ArrayList;

public class Board {

    private static volatile Board instance;
    private int[] playingBoard;

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
        System.out.println(new HashRep().decode(playingBoard));
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

    public void gameLoop(int playerW, int playerB, int[] board, boolean print, int depth) {
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
        while (areFieldsLeft() && evaluate() != 99999 && evaluate() != -99999) {
            if (print) printBoard();
            if (turn) {
                makeMove(playerWhite.fetchMove(print), turn);
                if (print) System.out.println("White made a move.");
                turn = false;
            } else {
                makeMove(playerBlack.fetchMove(print), turn);
                if (print) System.out.println("Black made a move.");
                turn = true;
            }
            if (evaluate() == 99999 || evaluate() == -99999) {
                break;
            }
        }
        if (print) printBoard();
        if (evaluate() == 99999) {
            if (print) System.out.println("\nWhite won.");
        } else if (evaluate() == -99999) {
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
        // winner eval
        final int winnerBlack = -99999;
        final int winnerWhite =  99999;
        int advantage = 0;
        // Check for horizontal winner
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (playingBoard[i * 6 + j + i] == 0) {
                    continue;
                }
                switch (playingBoard[i * 6 + j + i] + playingBoard[i * 6 + j + 1 + i] + playingBoard[i * 6 + j + 2 + i] + playingBoard[i * 6 + j + 3 + i]) {
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
                if (playingBoard[i + j * 7] == 0) {
                    continue;
                }
                switch (playingBoard[i + j * 7] + playingBoard[i + j * 7 + 7] + playingBoard[i + j * 7 + 14] + playingBoard[i + j * 7 + 21]) {
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
                if (playingBoard[j * 7 + i] == 0) {
                    continue;
                }
                switch (playingBoard[j * 7 + i] + playingBoard[j * 7 + i + 8] + playingBoard[j * 7 + i + 16] + playingBoard[j * 7 + i + 24]) {
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
                if (playingBoard[j * 7 + i + 3] == 0) {
                    continue;
                }
                switch (playingBoard[j * 7 + i + 3] + playingBoard[j * 7 + i + 3 + 6] + playingBoard[j * 7 + i + 3 + 12] + playingBoard[j * 7 + i + 3 + 18]) {
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
                if (playingBoard[i * 6 + j + i] == 0 && playingBoard[i * 6 + j + 4 + i] == 0) {
                    if (playingBoard[i * 6 + j + 1 + i] + playingBoard[i * 6 + j + 2 + i] + playingBoard[i * 6 + j + 3 + i] == 3) {
                        advantage += 4000;
                    } else if (playingBoard[i * 6 + j + 1 + i] + playingBoard[i * 6 + j + 2 + i] + playingBoard[i * 6 + j + 3 + i] == -3) {
                        advantage -= 4000;
                    } else if (playingBoard[i * 6 + j + 1 + i] == 1 && playingBoard[i * 6 + j + 2 + i] == 1 && playingBoard[i * 6 + j + 3 + i] == 0) {
                        advantage += 2000;
                    } else if (playingBoard[i * 6 + j + 1 + i] == 1 && playingBoard[i * 6 + j + 2 + i] == 0 && playingBoard[i * 6 + j + 3 + i] == 1) {
                        advantage += 2000;
                    } else if (playingBoard[i * 6 + j + 1 + i] == 0 && playingBoard[i * 6 + j + 2 + i] == 1 && playingBoard[i * 6 + j + 3 + i] == 1) {
                        advantage += 2000;
                    } else if (playingBoard[i * 6 + j + 1 + i] == -1 && playingBoard[i * 6 + j + 2 + i] == -1 && playingBoard[i * 6 + j + 3 + i] == 0) {
                        advantage -= 2000;
                    } else if (playingBoard[i * 6 + j + 1 + i] == -1 && playingBoard[i * 6 + j + 2 + i] == 0 && playingBoard[i * 6 + j + 3 + i] == -1) {
                        advantage -= 2000;
                    } else if (playingBoard[i * 6 + j + 1 + i] == 0 && playingBoard[i * 6 + j + 2 + i] == -1 && playingBoard[i * 6 + j + 3 + i] == -1) {
                        advantage -= 2000;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (playingBoard[j * 7 + i] == 0 && playingBoard[j * 7 + i + 32] == 0) {
                    if (playingBoard[j * 7 + i + 8] + playingBoard[j * 7 + i + 16] + playingBoard[j * 7 + i + 24] == 3) {
                        advantage += 4000;
                    } else if (playingBoard[j * 7 + i + 8] + playingBoard[j * 7 + i + 16] + playingBoard[j * 7 + i + 24] == -3) {
                        advantage -= 4000;
                    } else if (playingBoard[j * 7 + i + 8] == 1 && playingBoard[j * 7 + i + 16] == 1 && playingBoard[j * 7 + i + 24] == 0) {
                        advantage += 2000;
                    } else if (playingBoard[j * 7 + i + 8] == 1 && playingBoard[j * 7 + i + 16] == 0 && playingBoard[j * 7 + i + 24] == 1) {
                        advantage += 2000;
                    } else if (playingBoard[j * 7 + i + 8] == 0 && playingBoard[j * 7 + i + 16] == 1 && playingBoard[j * 7 + i + 24] == 1) {
                        advantage += 2000;
                    } else if (playingBoard[j * 7 + i + 8] == -1 && playingBoard[j * 7 + i + 16] == -1 && playingBoard[j * 7 + i + 24] == 0) {
                        advantage -= 2000;
                    } else if (playingBoard[j * 7 + i + 8] == -1 && playingBoard[j * 7 + i + 16] == 0 && playingBoard[j * 7 + i + 24] == -1) {
                        advantage -= 2000;
                    } else if (playingBoard[j * 7 + i + 8] == 0 && playingBoard[j * 7 + i + 16] == -1 && playingBoard[j * 7 + i + 24] == -1) {
                        advantage -= 2000;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (playingBoard[j * 7 + i + 4] == 0 && playingBoard[j * 7 + i + 4 + 24] == 0) {
                    if (playingBoard[j * 7 + i + 4 + 6] + playingBoard[j * 7 + i + 4 + 12] + playingBoard[j * 7 + i + 4 + 18] == 3) {
                        advantage += 4000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] + playingBoard[j * 7 + i + 4 + 12] + playingBoard[j * 7 + i + 4 + 18] == -3) {
                        advantage -= 4000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] == 1 && playingBoard[j * 7 + i + 4 + 12] == 1 && playingBoard[j * 7 + i + 4 + 18] == 0) {
                        advantage += 2000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] == 1 && playingBoard[j * 7 + i + 4 + 12] == 0 && playingBoard[j * 7 + i + 4 + 18] == 1) {
                        advantage += 2000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] == 0 && playingBoard[j * 7 + i + 4 + 12] == 1 && playingBoard[j * 7 + i + 4 + 18] == 1) {
                        advantage += 2000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] == -1 && playingBoard[j * 7 + i + 4 + 12] == -1 && playingBoard[j * 7 + i + 4 + 18] == 0) {
                        advantage -= 2000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] == -1 && playingBoard[j * 7 + i + 4 + 12] == 0 && playingBoard[j * 7 + i + 4 + 18] == -1) {
                        advantage -= 2000;
                    } else if (playingBoard[j * 7 + i + 4 + 6] == 0 && playingBoard[j * 7 + i + 4 + 12] == -1 && playingBoard[j * 7 + i + 4 + 18] == -1) {
                        advantage -= 2000;
                    }
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

    protected  void loadBB() {
        System.arraycopy(backupBoard, 0, playingBoard, 0, 42);
        turn = backupTurn;
    }

    public boolean getTurn() {
        return turn;
    }

    public boolean isColNotFull(int col) {
        return playingBoard[col - 1] == 0;
    }

    public void loadPosition(int[] board) {
        playingBoard = board;
    }

    public int winnerAfterMove(boolean turn, int winnerval, int loserval) {
        for (int i = 1; i <= 7; i++) {
            if (isColNotFull(i)) {
                makeMove(i, !turn);
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
