import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

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

    public void setPlayingBoard(int[] pBoard) {
        playingBoard = pBoard;
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
                    System.out.print(" ");
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
        System.out.println("Evaluation: " + evaluate());
    }

    private boolean turn = true;

    public void gameLoop(int playerW, int playerB, int[] board, boolean print, int depth, boolean waiter) throws ExecutionException, InterruptedException {
        playingBoard = board;
        Player playerWhite;
        Player playerBlack;
        if (playerW == 1) {
            playerWhite = new PlayerRandom();
        } else if (playerW == 2) {
            playerWhite = new PlayerMinimax(depth);
        } else if (playerW == 3) {
            playerWhite = new PlayerMCTS(depth);
        } else {
            playerWhite = new PlayerHuman();
        }
        if (playerB == 1) {
            playerBlack = new PlayerRandom();
        } else if (playerB == 2) {
            playerBlack = new PlayerMinimax(depth);
        } else if (playerB == 3) {
            playerBlack = new PlayerMCTS(depth);
        } else {
            playerBlack = new PlayerHuman();
        }
        while (areFieldsLeft(Board.getInstance().getPlayingBoard()) && evaluate() != 999999 && evaluate() != -999999) {
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
        final int bestAdvantage = 50000;
        final int goodAdvantage = 25000;
        final int okAdvantage =   1000;
        int advantage = 0;
        // Check for horizontal winner & forces
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
//                if (position[i * 6 + j + i] == 0) {
//                    continue;
//                }
                switch (position[i * 6 + j + i] + position[i * 6 + j + 1 + i] + position[i * 6 + j + 2 + i] + position[i * 6 + j + 3 + i]) {
                    case 4 -> {
//                        System.out.println("eval: White won");
                        return winnerWhite;
                    }
                    case -4 -> {
//                        System.out.println("eval: Black won");
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
//                if (position[i + j * 7] == 0) {
//                    continue;
//                }
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
        //   - Left Top Right Bottom
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
//                if (position[j * 7 + i] == 0) {
//                    continue;
//                }
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
        //   - Left Bottom Right Top
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
//                if (position[j * 7 + i + 3] == 0) {
//                    continue;
//                }
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
        // Is there a draw
        if (!areFieldsLeft(position)) return 0;
//        // Traps [-xxx-] & Pre-traps [-x-x-]
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
        //   - Pre-seven [???-][xx--][?x??][x???]
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
//                if (position[i * 6 + j + i] == 0) {
//                    continue;
//                }
                if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 8] + position[i * 6 + j + i + 14] == 4 && position[i * 6 + j + i - 4] != -1 && position[i * 6 + j + i + 2] != -1 && position[i * 6 + j + i + 3] != -1) {
                    advantage += goodAdvantage;
                } else if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 8] + position[i * 6 + j + i + 14] == -4 && position[i * 6 + j + i - 4] != 1 && position[i * 6 + j + i + 2] != 1 && position[i * 6 + j + i + 3] != 1) {
                    advantage -= goodAdvantage;
                } else if (position[i * 6 + j + i] != -1 && position[i * 6 + j + i - 7] != -1 && position[i * 6 + j + i + 8] == 1 && position[i * 6 + j + i + 1] == 1 && position[i * 6 + j + i + 2] == 1 && position[i * 6 + j + i + 16] == 1) {
                    advantage += goodAdvantage;
                } else if (position[i * 6 + j + i] != 1 && position[i * 6 + j + i - 7] != 1 && position[i * 6 + j + i + 8] == -1 && position[i * 6 + j + i + 1] == -1 && position[i * 6 + j + i + 2] == -1 && position[i * 6 + j + i + 16] == -1) {
                    advantage -= goodAdvantage;
                }
            }
            for (int j = 0; j < 4; j++) {
                if (position[i * 6 + j + i - 7] == 1 && position[i * 6 + j + i + 1] == 1 && position[i * 6 + j + i + 7] == 1 && position[i * 6 + j + i + 8] == 1 && position[i * 6 + j + i + 9] != -1 && position[i * 6 + j + i + 17] != -1 && position[i * 6 + j + i + 10] != -1) {
                    advantage += goodAdvantage;
                } else if (position[i * 6 + j + i - 4] == -1 && position[i * 6 + j + i + 2] == -1 && position[i * 6 + j + i + 9] == -1 && position[i * 6 + j + i + 10] == -1 && position[i * 6 + j + i + 7] != 1 && position[i * 6 + j + i + 8] != 1 && position[i * 6 + j + i + 14] != 1) {
                    advantage -= goodAdvantage;
                } else if (position[i * 6 + j + i - 7] == -1 && position[i * 6 + j + i + 1] == -1 && position[i * 6 + j + i + 7] == -1 && position[i * 6 + j + i + 8] == -1 && position[i * 6 + j + i + 9] != 1 && position[i * 6 + j + i + 17] != 1 && position[i * 6 + j + i + 10] != 1) {
                    advantage -= goodAdvantage;
                } else if (position[i * 6 + j + i - 4] == 1 && position[i * 6 + j + i + 2] == 1 && position[i * 6 + j + i + 9] == 1 && position[i * 6 + j + i + 10] == 1 && position[i * 6 + j + i + 7] != -1 && position[i * 6 + j + i + 8] != -1 && position[i * 6 + j + i + 14] != -1) {
                    advantage += goodAdvantage;
                }
            }
        }
        //   - Seven [???-][xxx-][?x??][x???]
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (position[i * 6 + j + i] == 0) {
                    continue;
                }
                if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 8] + position[i * 6 + j + i + 14] == 4 && position[i * 6 + j + i - 4] != -1 && position[i * 6 + j + i + 2] == 1 && position[i * 6 + j + i + 3] != -1) {
                    advantage += bestAdvantage;
                } else if (position[i * 6 + j + i] + position[i * 6 + j + i + 1] + position[i * 6 + j + i + 8] + position[i * 6 + j + i + 14] == -4 && position[i * 6 + j + i - 4] != 1 && position[i * 6 + j + i + 2] == -1 && position[i * 6 + j + i + 3] != 1) {
                    advantage -= bestAdvantage;
                }
            }
            for (int j = 0; j < 4; j++) {
                if (position[i * 6 + j + i - 7] == 1 && position[i * 6 + j + i + 1] == 1 && position[i * 6 + j + i + 7] == 1 && position[i * 6 + j + i + 8] == 1 && position[i * 6 + j + i + 9] == 1 && position[i * 6 + j + i + 17] != -1 && position[i * 6 + j + i + 10] != -1) {
                    advantage += goodAdvantage;
                } else if (position[i * 6 + j + i - 4] == -1 && position[i * 6 + j + i + 2] == -1 && position[i * 6 + j + i + 9] == -1 && position[i * 6 + j + i + 10] == -1 && position[i * 6 + j + i + 7] != 1 && position[i * 6 + j + i + 8] == -1 && position[i * 6 + j + i + 14] != 1) {
                    advantage -= goodAdvantage;
                } else if (position[i * 6 + j + i - 7] == -1 && position[i * 6 + j + i + 1] == -1 && position[i * 6 + j + i + 7] == -1 && position[i * 6 + j + i + 8] == -1 && position[i * 6 + j + i + 9] == -1 && position[i * 6 + j + i + 17] != 1 && position[i * 6 + j + i + 10] != 1) {
                    advantage -= goodAdvantage;
                } else if (position[i * 6 + j + i - 4] == 1 && position[i * 6 + j + i + 2] == 1 && position[i * 6 + j + i + 9] == 1 && position[i * 6 + j + i + 10] == 1 && position[i * 6 + j + i + 7] != -1 && position[i * 6 + j + i + 8] == 1 && position[i * 6 + j + i + 14] != -1) {
                    advantage += goodAdvantage;
                }
            }
        }
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (position[i * 6 + j + i] != -1 && position[i * 6 + j + i - 7] != -1 && position[i * 6 + j + i + 8] == 1 && position[i * 6 + j + i + 1] == 1 && position[i * 6 + j + i + 2] == 1 && position[i * 6 + j + i + 16] == 1 && position[i * 6 + j + i + 24] == 1 && position[i * 6 + j + i + 3] == 1) {
                    advantage += bestAdvantage;
                } else if (position[i * 6 + j + i] != 1 && position[i * 6 + j + i - 7] != 1 && position[i * 6 + j + i + 8] == -1 && position[i * 6 + j + i + 1] == -1 && position[i * 6 + j + i + 2] == -1 && position[i * 6 + j + i + 16] == -1 && position[i * 6 + j + i + 24] == -1 && position[i * 6 + j + i + 3] == -1) {
                    advantage -= bestAdvantage;
                }
            }
        }
        //   - Banana
        for (int i = 0; i < 3; i++) {
            if (position[i * 6 + i + 6] == 1 && position[i * 6 + i + 12] == 1 && position[i * 6 + i + 14] == 1 && position[i * 6 + i + 15] == 1 && position[i * 6 + i + 16] == 1 && position[i * 6 + i + 18] == 1  && position[i * 6 + i + 17] != -1 && position[i * 6 + i + 24] != -1) {
                advantage += bestAdvantage;
            } else if (position[i * 6 + i + 6] == -1 && position[i * 6 + i + 12] == -1 && position[i * 6 + i + 14] == -1 && position[i * 6 + i + 15] == -1 && position[i * 6 + i + 16] == -1 && position[i * 6 + i + 18] == -1 && position[i * 6 + i + 17] != 1 && position[i * 6 + i + 24] != 1) {
                advantage -= bestAdvantage;
            } else if (position[i * 6 + i + 7] == 1 && position[i * 6 + i + 8] == 1 && position[i * 6 + i + 9] == 1 && position[i * 6 + i + 11] == 1 && position[i * 6 + i + 19] == 1 && position[i * 6 + i + 27] == 1  && position[i * 6 + i + 3] != -1 && position[i * 6 + i + 10] != -1) {
                advantage += bestAdvantage;
            } else if (position[i * 6 + i + 7] == -1 && position[i * 6 + i + 8] == -1 && position[i * 6 + i + 9] == -1 && position[i * 6 + i + 11] == -1 && position[i * 6 + i + 19] == -1 && position[i * 6 + i + 27] == -1  && position[i * 6 + i + 3] != 1 && position[i * 6 + i + 10] != 1) {
                advantage -= bestAdvantage;
            } else if (position[i * 6 + i] == 1 && position[i * 6 + i + 8] == 1 && position[i * 6 + i + 16] == 1 && position[i * 6 + i + 18] == 1 && position[i * 6 + i + 19] == 1 && position[i * 6 + i + 20] == 1  && position[i * 6 + i + 17] != -1 && position[i * 6 + i + 24] != -1) {
                advantage += bestAdvantage;
            } else if (position[i * 6 + i] == -1 && position[i * 6 + i + 8] == -1 && position[i * 6 + i + 16] == -1 && position[i * 6 + i + 18] == -1 && position[i * 6 + i + 19] == -1 && position[i * 6 + i + 20] == -1  && position[i * 6 + i + 17] != 1 && position[i * 6 + i + 24] != 1) {
                advantage -= bestAdvantage;
            } else if (position[i * 6 + i + 9] == 1 && position[i * 6 + i + 15] == 1 && position[i * 6 + i + 21] == 1 && position[i * 6 + i + 11] == 1 && position[i * 6 + i + 12] == 1 && position[i * 6 + i + 13] == 1  && position[i * 6 + i + 3] != -1 && position[i * 6 + i + 10] != -1) {
                advantage += bestAdvantage;
            } else if (position[i * 6 + i + 9] == -1 && position[i * 6 + i + 15] == -1 && position[i * 6 + i + 21] == -1 && position[i * 6 + i + 11] == -1 && position[i * 6 + i + 12] == -1 && position[i * 6 + i + 13] == -1  && position[i * 6 + i + 3] != 1 && position[i * 6 + i + 10] != 1) {
                advantage -= bestAdvantage;
            }
        }
        //   - Pre-Banana
        for (int i = 0; i < 3; i++) {
            if (position[i * 6 + i + 6] + position[i * 6 + i + 12] + position[i * 6 + i + 14] + position[i * 6 + i + 15] + position[i * 6 + i + 16] + position[i * 6 + i + 18] == 5  && position[i * 6 + i + 17] != -1 && position[i * 6 + i + 24] != -1) {
                advantage += goodAdvantage;
            } else if (position[i * 6 + i + 6] + position[i * 6 + i + 12] + position[i * 6 + i + 14] + position[i * 6 + i + 15] + position[i * 6 + i + 16] + position[i * 6 + i + 18] == -5 && position[i * 6 + i + 17] != 1 && position[i * 6 + i + 24] != 1) {
                advantage -= goodAdvantage;
            } else if (position[i * 6 + i + 7] + position[i * 6 + i + 8] + position[i * 6 + i + 9] + position[i * 6 + i + 11] + position[i * 6 + i + 19] + position[i * 6 + i + 27] == 5  && position[i * 6 + i + 3] != -1 && position[i * 6 + i + 10] != -1) {
                advantage += goodAdvantage;
            } else if (position[i * 6 + i + 7] + position[i * 6 + i + 8] + position[i * 6 + i + 9] + position[i * 6 + i + 11] + position[i * 6 + i + 19] + position[i * 6 + i + 27] == -5  && position[i * 6 + i + 3] != 1 && position[i * 6 + i + 10] != 1) {
                advantage -= goodAdvantage;
            } else if (position[i * 6 + i] + position[i * 6 + i + 8] + position[i * 6 + i + 16] + position[i * 6 + i + 18] + position[i * 6 + i + 19] + position[i * 6 + i + 20] == 5  && position[i * 6 + i + 17] != -1 && position[i * 6 + i + 24] != -1) {
                advantage += goodAdvantage;
            } else if (position[i * 6 + i] + position[i * 6 + i + 8] + position[i * 6 + i + 16] + position[i * 6 + i + 18] + position[i * 6 + i + 19] + position[i * 6 + i + 20] == -5  && position[i * 6 + i + 17] != 1 && position[i * 6 + i + 24] != 1) {
                advantage -= goodAdvantage;
            } else if (position[i * 6 + i + 9] + position[i * 6 + i + 15] + position[i * 6 + i + 21] + position[i * 6 + i + 11] + position[i * 6 + i + 12] + position[i * 6 + i + 13] == 5  && position[i * 6 + i + 3] != -1 && position[i * 6 + i + 10] != -1) {
                advantage += goodAdvantage;
            } else if (position[i * 6 + i + 9] + position[i * 6 + i + 15] + position[i * 6 + i + 21] + position[i * 6 + i + 11] + position[i * 6 + i + 12] + position[i * 6 + i + 13] == -5  && position[i * 6 + i + 3] != 1 && position[i * 6 + i + 10] != 1) {
                advantage -= goodAdvantage;
            }
        }
        //   - Four
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (position[i * 6 + i + 3 + j] == 1 && position[i * 6 + i + 7 + j] == 1 && position[i * 6 + i + 9 + j] == 1 && position[i * 6 + i + 10 + j] == 1 && position[i * 6 + i + 21 + j] == 1 && position[i * 6 + i + 8 + j] != -1 && position[i * 6 + i + 15 + j] != -1) {
                    advantage += bestAdvantage;
                } else if (position[i * 6 + i + 3 + j] == -1 && position[i * 6 + i + 7 + j] == -1 && position[i * 6 + i + 9 + j] == -1 && position[i * 6 + i + 10 + j] == -1 && position[i * 6 + i + 21 + j] == -1 && position[i * 6 + i + 8 + j] != 1 && position[i * 6 + i + 15 + j] != 1) {
                    advantage -= bestAdvantage;
                } else if (position[i * 6 + i] + j == 1 && position[i * 6 + i + 7 + j] == 1 && position[i * 6 + i + 8 + j] == 1 && position[i * 6 + i + 10 + j] == 1 && position[i * 6 + i + j + 24] == 1 && position[i * 6 + i + 9 + j] != -1 && position[i * 6 + i + 16 + j] != -1) {
                    advantage += bestAdvantage;
                } else if (position[i * 6 + i + j] == -1 && position[i * 6 + i + 7 + j] == -1 && position[i * 6 + i + 8 + j] == -1 && position[i * 6 + i + 10 + j] == -1 && position[i * 6 + i + 24 + j] == -1 && position[i * 6 + i + 9 + j] != 1 && position[i * 6 + i + 16 + j] != 1) {
                    advantage -= bestAdvantage;
                } else if (position[i * 6 + i + j] == 1 && position[i * 6 + i + 14 + j] == 1 && position[i * 6 + i + 16 + j] == 1 && position[i * 6 + i + 17 + j] == 1 && position[i * 6 + i + 24 + j] == 1 && position[i * 6 + i + 8 + j] != -1 && position[i * 6 + i + 15 + j] != -1) {
                    advantage += bestAdvantage;
                } else if (position[i * 6 + i + j] == -1 && position[i * 6 + i + 14 + j] == -1 && position[i * 6 + i + 16 + j] == -1 && position[i * 6 + i + 17 + j] == -1 && position[i * 6 + i + 24 + j] == -1 && position[i * 6 + i + 8 + j] != 1 && position[i * 6 + i + 15 + j] != 1) {
                    advantage -= bestAdvantage;
                } else if (position[i * 6 + i + 3 + j] == 1 && position[i * 6 + i + 14 + j] == 1 && position[i * 6 + i + 15 + j] == 1 && position[i * 6 + i + 17 + j] == 1 && position[i * 6 + i + 21 + j] == 1 && position[i * 6 + i + 9 + j] != -1 && position[i * 6 + i + 16 + j] != -1) {
                    advantage += bestAdvantage;
                } else if (position[i * 6 + i + 3 + j] == -1 && position[i * 6 + i + 14 + j] == -1 && position[i * 6 + i + 15 + j] == -1 && position[i * 6 + i + 17 + j] == -1 && position[i * 6 + i + 21 + j] == -1 && position[i * 6 + i + 9 + j] != 1 && position[i * 6 + i + 16 + j] != 1) {
                    advantage -= bestAdvantage;
                }
            }

        }
        //   - Pre-Four
        //   - Long Stick
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (position[i * 6 + i + j + 4] + position[i * 6 + i + j + 10] + position[i * 6 + i + j + 21] + position[i * 6 + i + j + 22] + position[i * 6 + i + j + 24]) {
                    case 5: if (position[i * 6 + i + j + 16] != -1 && position[i * 6 + i + j + 23] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 16] != 1 && position[i * 6 + i + j + 23] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 16] != -1 && position[i * 6 + i + j + 23] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 16] != 1 && position[i * 6 + i + j + 23] != 1) advantage -= goodAdvantage;
                }
                switch (position[i * 6 + i + j] + position[i * 6 + i + j + 8] + position[i * 6 + i + j + 25] + position[i * 6 + i + j + 22] + position[i * 6 + i + j + 24]) {
                    case 5: if (position[i * 6 + i + j + 16] != -1 && position[i * 6 + i + j + 23] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 16] != 1 && position[i * 6 + i + j + 23] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 16] != -1 && position[i * 6 + i + j + 23] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 16] != 1 && position[i * 6 + i + j + 23] != 1) advantage -= goodAdvantage;
                }
                switch (position[i * 6 + i + j + 4] + position[i * 6 + i + j + 1] + position[i * 6 + i + j + 3] + position[i * 6 + i + j + 15] + position[i * 6 + i + j + 21]) {
                    case 5: if (position[i * 6 + i + j + 2] != -1 && position[i * 6 + i + j + 9] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 2] != 1 && position[i * 6 + i + j + 9] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 2] != -1 && position[i * 6 + i + j + 9] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 2] != 1 && position[i * 6 + i + j + 9] != 1) advantage -= goodAdvantage;
                }
                switch (position[i * 6 + i + j] + position[i * 6 + i + j + 1] + position[i * 6 + i + j + 3] + position[i * 6 + i + j + 17] + position[i * 6 + i + j + 25]) {
                    case 5: if (position[i * 6 + i + j + 2] != -1 && position[i * 6 + i + j + 9] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 2] != 1 && position[i * 6 + i + j + 9] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 2] != -1 && position[i * 6 + i + j + 9] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 2] != 1 && position[i * 6 + i + j + 9] != 1) advantage -= goodAdvantage;
                }
            }
        }
        //   - Short Stick
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                switch (position[i * 6 + i + j + 3] + position[i * 6 + i + j + 9] + position[i * 6 + i + j + 21] + position[i * 6 + i + j + 23] + position[i * 6 + i + j + 24]) {
                    case 5: if (position[i * 6 + i + j + 15] != -1 && position[i * 6 + i + j + 22] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 15] != 1 && position[i * 6 + i + j + 22] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 15] != -1 && position[i * 6 + i + j + 22] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 15] != 1 && position[i * 6 + i + j + 22] != 1) advantage -= goodAdvantage;
                }
                switch (position[i * 6 + i + j] + position[i * 6 + i + j + 8] + position[i * 6 + i + j + 21] + position[i * 6 + i + j + 22] + position[i * 6 + i + j + 24]) {
                    case 5: if (position[i * 6 + i + j + 16] != -1 && position[i * 6 + i + j + 23] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 16] != 1 && position[i * 6 + i + j + 23] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 16] != -1 && position[i * 6 + i + j + 23] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 16] != 1 && position[i * 6 + i + j + 23] != 1) advantage -= goodAdvantage;
                }
                switch (position[i * 6 + i + j] + position[i * 6 + i + j + 2] + position[i * 6 + i + j + 3] + position[i * 6 + i + j + 16] + position[i * 6 + i + j + 24]) {
                    case 5: if (position[i * 6 + i + j + 1] != -1 && position[i * 6 + i + j + 8] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 1] != 1 && position[i * 6 + i + j + 8] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 1] != -1 && position[i * 6 + i + j + 8] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 1] != 1 && position[i * 6 + i + j + 8] != 1) advantage -= goodAdvantage;
                }
                switch (position[i * 6 + i + j] + position[i * 6 + i + j + 1] + position[i * 6 + i + j + 3] + position[i * 6 + i + j + 15] + position[i * 6 + i + j + 21]) {
                    case 5: if (position[i * 6 + i + j + 2] != -1 && position[i * 6 + i + j + 9] != -1) advantage += bestAdvantage;
                    case -5: if (position[i * 6 + i + j + 2] != 1 && position[i * 6 + i + j + 9] != 1) advantage -= bestAdvantage;
                    case 4: if (position[i * 6 + i + j + 2] != -1 && position[i * 6 + i + j + 9] != -1) advantage += goodAdvantage;
                    case -4: if (position[i * 6 + i + j + 2] != 1 && position[i * 6 + i + j + 9] != 1) advantage -= goodAdvantage;
                }
            }
        }
        // Not a terminal state, returning advantage
        return advantage;
    }

    protected boolean areFieldsLeft(int[] position) {
        for (int i = 0; i < 42; i++) {
            if (position[i] == 0) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<Integer> legalMoves() {
        return legalMoves(playingBoard);
    }

    protected ArrayList<Integer> legalMoves(int[] position) {
        ArrayList<Integer>  outArr = new ArrayList<>(0);
        for (int i = 1; i <= 7; i++) {
            if (position[i - 1] == 0) {
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

    public void setTurn(boolean turn) {
        this.turn = turn;
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

    public boolean gameOver(int[] playingBoard) {
        int eval = evaluate(playingBoard);
        return eval == 999999 || eval == -999999 || !areFieldsLeft(playingBoard);
    }

    public boolean gameOver() {
        return gameOver(getPlayingBoard());
    }
}
