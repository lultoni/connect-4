import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class PlayerMCTS extends Player {

    public PlayerMCTS(int depth) {
        super(depth);
    }

    Board board = Board.getInstance();
    int[] playingBoard = board.getPlayingBoard();
    protected void assessCols(boolean turn, int winnerval, int loserval, boolean print, int colGamesToPlay) throws ExecutionException, InterruptedException {
        if (board.isColNotFull(1)) {
            board.createBB();
            board.makeMove(1, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col1val = 0; else if (comp == winnerval) col1val = colGamesToPlay; else col1val = search(colGamesToPlay, winnerval);
//            col1val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("1: " + col1val);
        }
        if (board.isColNotFull(2)) {
            board.createBB();
            board.makeMove(2, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col2val = 0; else if (comp == winnerval) col2val = colGamesToPlay; else col2val = search(colGamesToPlay, winnerval);
//            col2val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("2: " + col2val);
        }
        if (board.isColNotFull(3)) {
            board.createBB();
            board.makeMove(3, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col3val = 0; else if (comp == winnerval) col3val = colGamesToPlay; else col3val = search(colGamesToPlay, winnerval);
//            col3val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("3: " + col3val);
        }
        if (board.isColNotFull(4)) {
            board.createBB();
            board.makeMove(4, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col4val = 0; else if (comp == winnerval) col4val = colGamesToPlay; else col4val = search(colGamesToPlay, winnerval);
//            col4val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("4: " + col4val);
        }
        if (board.isColNotFull(5)) {
            board.createBB();
            board.makeMove(5, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col5val = 0; else if (comp == winnerval) col5val = colGamesToPlay; else col5val = search(colGamesToPlay, winnerval);
//            col5val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("5: " + col5val);
        }
        if (board.isColNotFull(6)) {
            board.createBB();
            board.makeMove(6, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col6val = 0; else if (comp == winnerval) col6val = colGamesToPlay; else col6val = search(colGamesToPlay, winnerval);
//            col6val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("6: " + col6val);
        }
        if (board.isColNotFull(7)) {
            board.createBB();
            board.makeMove(7, turn);
            int comp = board.winnerAfterMove(!turn, winnerval, loserval);
            if (comp == loserval) col7val = 0; else if (comp == winnerval) col7val = colGamesToPlay; else col7val = search(colGamesToPlay, winnerval);
//            col7val = search(colGamesToPlay, winnerval);
            board.loadBB();
            if (print) System.out.println("7: " + col7val);
        }
    }

    @Override
    public int fetchMove(boolean print) throws ExecutionException, InterruptedException {
        if (print) System.out.println("MCTS fetch call");
        int winnerval;
        int loserval;
        if (Board.getInstance().getTurn()) {
            winnerval = 999999;
            loserval = -999999;
        } else {
            winnerval = -999999;
            loserval = 999999;
        }
        assessCols(board.getTurn(), winnerval, loserval, print, 10000);
        int bestcol = 0;
        int bestval;
        if (col1val != 69420) bestcol = 1; bestval = col1val;
        if (col2val > bestval && col2val != 69420) bestcol = 2; bestval = col2val;
        if (col3val > bestval && col3val != 69420) bestcol = 3; bestval = col3val;
        if (col4val > bestval && col4val != 69420) bestcol = 4; bestval = col4val;
        if (col5val > bestval && col5val != 69420) bestcol = 5; bestval = col5val;
        if (col6val > bestval && col6val != 69420) bestcol = 6; bestval = col6val;
        if (col7val > bestval && col7val != 69420) bestcol = 7;
        int[] valArr = {col1val, col2val, col3val, col4val, col5val, col6val, col7val};
        if (print) System.out.println("MCTS best col is " + bestcol + "\n" + Arrays.toString(valArr));
        return bestcol;
    }

    private int search(int colGamesToPlay, int winnerValue) throws ExecutionException, InterruptedException {
        int[] startPosition = new int[42];
        System.arraycopy(playingBoard, 0, startPosition, 0, 42);
        boolean startTurn = !board.getTurn();
        Player player = new PlayerRandom();
        int gamesWon = 0;
        for (int currentGame = 0; currentGame < colGamesToPlay; currentGame++) {
            System.arraycopy(playingBoard, 0, startPosition, 0, 42);
            board.loadPosition(startPosition);
            boolean turn = startTurn;
            while (true) {
                if (board.gameOver()) {
                    if (board.evaluate() == winnerValue) {
                        gamesWon++;
                    }
                    break;
                }
                board.makeMove(player.fetchMove(false), turn);
                turn = !turn;
            }
        }
        return gamesWon;
    }

}
