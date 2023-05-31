import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Board {

    private static volatile Board instance;
    private int[] playingBoard;
    protected static boolean wasInput = false;
    protected static boolean skip_out_of_game_loop = false;

    protected String gameMoves = "";
    protected int playerInput = 0;

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
        System.out.println("~~~~~~~~~~\nPosition-ID: " + HashRep.decode(playingBoard));
        System.out.println("Position: " + Arrays.toString(playingBoard));
        System.out.println("Moves: " + gameMoves);
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
                } while (Math.round((endTime - startTime) / 1e+6) < 200);
            }
            skip_out_of_game_loop = false;
            if (turn) {
                if (print) System.out.println("?> White Starts Thinking");
                if (!playerWhite.getClass().equals(PlayerHuman.class)) {
                    int whiteMove = playerWhite.fetchMove(print);
                    makeMove(whiteMove, turn);
                    gameMoves += whiteMove;
                } else {
                    wasInput = false;
                    while (!wasInput && !skip_out_of_game_loop) {
                        Thread.onSpinWait();
                    }
                    gameMoves += String.valueOf(playerInput);
                }
                if (print) System.out.println("?> White made a move.");
                turn = false;
            } else {
                if (print) System.out.println("?> Black Starts Thinking");
                if (!playerBlack.getClass().equals(PlayerHuman.class)) {
                    int blackMove = playerBlack.fetchMove(print);
                    makeMove(blackMove, turn);
                    gameMoves += blackMove;
                } else {
                    wasInput = false;
                    while (!wasInput && !skip_out_of_game_loop) {
                        Thread.onSpinWait();
                    }
                    gameMoves += String.valueOf(playerInput);
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
        final int bestAdvantage = 5000;
        final int goodAdvantage = 2500;
        final int okAdvantage =   1000;
        int advantage = 0;
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 7; row++) {
                int index = column * 6 + column + row;
                // Row Winner, Forces
                if (row < 4) switch (position[index] + position[index + 1] + position[index + 2] + position[index + 3]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
                // Column Winner, Forces
                if (column < 3) switch (position[index] + position[index + 7] + position[index + 14] + position[index + 21]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
                // Diagonal Winner, Forces
                //   - Left Top Right Bottom
                if (row < 4 && column < 3) switch (position[index] + position[index + 8] + position[index + 16] + position[index + 24]) {
                    case 4 -> {
                        return winnerWhite;
                    }
                    case -4 -> {
                        return winnerBlack;
                    }
                    case 3 -> advantage += okAdvantage;
                    case -3 -> advantage -= okAdvantage;
                }
                //   - Left Bottom Right Top
                if (row < 4 && column < 3) switch (position[index + 21] + position[index + 15] + position[index + 9] + position[index + 3]) {
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
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 7; row++) {
                int index = column * 6 + column + row;
                if (row < 3) {
                    // Horizontal Trap, Pre-Trap
                    if (position[index] == 0 && position[index + 4] == 0) {
                        switch (position[index + 1] + position[index + 2] + position[index + 3]) {
                            case 3: advantage += bestAdvantage;
                            case -3: advantage -= bestAdvantage;
                            case 2: advantage += goodAdvantage;
                            case -2: advantage -= goodAdvantage;
                        }
                    }
                    if (column < 2) {
                        // Diagonal Trap, Forces
                        //   - Left Top Right Bottom
                        if (position[index] == 0 && position[index + 32] == 0) {
                            switch (position[index + 8] + position[index + 16] + position[index + 24]) {
                                case 3: advantage += bestAdvantage;
                                case -3: advantage -= bestAdvantage;
                                case 2: advantage += goodAdvantage;
                                case -2: advantage -= goodAdvantage;
                            }
                        }
                        //   - Left Bottom Right Top
                        if (position[index + 4] == 0 && position[index + 28] == 0) {
                            switch (position[index + 10] + position[index + 16] + position[index + 22]) {
                                case 3: advantage += bestAdvantage;
                                case -3: advantage -= bestAdvantage;
                                case 2: advantage += goodAdvantage;
                                case -2: advantage -= goodAdvantage;
                            }
                        }
                    }
                    // Long Stick Trap, Pre-Trap
                    if (column < 3) {
                        if (position[index + 2] == 0 && position[index + 9] == 0) {
                            // Normal
                            switch (position[index] + position[index + 1] + position[index + 3] + position[index + 17] + position[index + 25]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                            // Reverse
                            switch (position[index + 1] + position[index + 3] + position[index + 4] + position[index + 15] + position[index + 21]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                        }
                        if (position[index + 16] == 0 && position[index + 23] == 0) {
                            // Flip
                            switch (position[index + 21] + position[index + 22] + position[index + 24] + position[index + 10] + position[index + 4]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                            // Flip Reverse
                            switch (position[index + 25] + position[index + 22] + position[index + 24] + position[index + 8] + position[index]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                        }
                    }
                    // Short Stick Trap, Pre-Trap
                    if (column < 3) {
                        // Normal
                        if (position[index + 15] == 0 && position[index + 22] == 0) {
                            switch (position[index + 3] + position[index + 9] + position[index + 21] + position[index + 23] + position[index + 24]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                        }
                        // Reverse
                        if (position[index + 16] == 0 && position[index + 23] == 0) {
                            switch (position[index] + position[index + 8] + position[index + 21] + position[index + 22] + position[index + 24]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                        }
                        // Flip Normal
                        if (position[index + 1] == 0 && position[index + 8] == 0) {
                            switch (position[index] + position[index + 2] + position[index + 3] + position[index + 16] + position[index + 24]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                        }
                        // Flip Reverse
                        if (position[index + 2] == 0 && position[index + 9] == 0) {
                            switch (position[index] + position[index + 1] + position[index + 3] + position[index + 15] + position[index + 21]) {
                                case 5: advantage += bestAdvantage;
                                case -5: advantage -= bestAdvantage;
                                case 4: advantage += goodAdvantage;
                                case -4: advantage -= goodAdvantage;
                            }
                        }
                    }
                }
                // Vertical Trap, Pre-Trap
                if (column < 2 && position[index] == 0 && position[index + 28] == 0) {
                    switch (position[index + 7] + position[index + 14] + position[index + 21]) {
                        case 3: advantage += bestAdvantage;
                        case -3: advantage -= bestAdvantage;
                        case 2: advantage += goodAdvantage;
                        case -2: advantage -= goodAdvantage;
                    }
                }
                if (row < 4 && column < 3) {
                    // Seven Trap, Pre-Trap
                    // Normal
                    if (position[index + 3] == 0 && position[index + 10] == 0) {
                        switch (position[index + 7] + position[index + 8] + position[index + 9] + position[index + 15] + position[index + 21]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                    }
                    // Reverse
                    if (position[index] == 0 && position[index + 7] == 0) {
                        switch (position[index + 8] + position[index + 9] + position[index + 10] + position[index + 15] + position[index + 23]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                    }
                    // Flip
                    if (position[index + 17] == 0 && position[index + 24] == 0) {
                        switch (position[index + 14] + position[index + 15] + position[index + 16] + position[index + 8] + position[index]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                    }
                    // Flip Reverse
                    if (position[index + 14] == 0 && position[index + 21] == 0) {
                        switch (position[index + 15] + position[index + 16] + position[index + 17] + position[index + 9] + position[index + 3]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                    }
                    // Four Trap, Pre-Trap
                    if (position[index + 9] == 0 && position[index + 16] == 0) {
                        // Normal
                        switch (position[index + 3] + position[index + 14] + position[index + 15] + position[index + 17] + position[index + 21]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                        // Flip
                        switch (position[index] + position[index + 7] + position[index + 8] + position[index + 10] + position[index + 24]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                    }
                    if (position[index + 8] == 0 && position[index + 15] == 0) {
                        // Reverse
                        switch (position[index + 14] + position[index + 16] + position[index + 17] + position[index + 24] + position[index]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                        // Flip Reverse
                        switch (position[index + 3] + position[index + 9] + position[index + 10] + position[index + 7] + position[index + 21]) {
                            case 5: advantage += bestAdvantage;
                            case -5: advantage -= bestAdvantage;
                            case 4: advantage += goodAdvantage;
                            case -4: advantage -= goodAdvantage;
                        }
                    }
                }
                // Banana Trap, Pre-Trap
                if (row == 0 && column < 3) {
                    if (position[index + 17] == 0 && position[index + 24] == 0) {
                        // Normal
                        switch (position[index + 14] + position[index + 15] + position[index + 16] + position[index + 18] + position[index + 12] + position[index + 16]) {
                            case 6: advantage += bestAdvantage;
                            case -6: advantage -= bestAdvantage;
                            case 5: advantage += goodAdvantage;
                            case -5: advantage -= goodAdvantage;
                        }
                        // Reverse
                        switch (position[index + 14] + position[index + 15] + position[index + 16] + position[index + 18] + position[index + 12] + position[index + 16]) {
                            case 6: advantage += bestAdvantage;
                            case -6: advantage -= bestAdvantage;
                            case 5: advantage += goodAdvantage;
                            case -5: advantage -= goodAdvantage;
                        }
                    }
                    if (position[index + 3] == 0 && position[index + 10] == 0) {
                        // Flip
                        switch (position[index] + position[index + 1] + position[index + 2] + position[index + 11] + position[index + 19] + position[index + 27]) {
                            case 6: advantage += bestAdvantage;
                            case -6: advantage -= bestAdvantage;
                            case 5: advantage += goodAdvantage;
                            case -5: advantage -= goodAdvantage;
                        }
                        // Flip Reverse
                        switch (position[index + 4] + position[index + 5] + position[index + 6] + position[index + 9] + position[index + 15] + position[index + 21]) {
                            case 6: advantage += bestAdvantage;
                            case -6: advantage -= bestAdvantage;
                            case 5: advantage += goodAdvantage;
                            case -5: advantage -= goodAdvantage;
                        }
                    }
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
        int current_eval = evaluate();
        if (current_eval == winnerval || current_eval == loserval) return current_eval;
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
