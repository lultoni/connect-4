import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static volatile boolean gameStart;
    static int pt1 = 0;
    static int pt2 = 0;
    static int depth = 10;

    public static void main (String[] args) throws ExecutionException, InterruptedException {

        System.out.println("Running Connect Four by CallMeLee...");

        HashRep.initializeZobristTable();

        HashRep.addOpening("", 4);
        HashRep.addOpening("4", 4);
        HashRep.addOpening("41", 4);
        HashRep.addOpening("4141", 5);
        HashRep.addOpening("4144", 4);
        HashRep.addOpening("4145", 4);
        HashRep.addOpening("47", 4);
        HashRep.addOpening("4747", 3);
        HashRep.addOpening("4744", 4);
        HashRep.addOpening("4743", 4);
        HashRep.addOpening("5", 4);
        HashRep.addOpening("54", 4);
        HashRep.addOpening("544", 4);
        HashRep.addOpening("5444", 4);
        HashRep.addOpening("6", 5);
        HashRep.addOpening("7", 4);
        HashRep.addOpening("3", 4);
        HashRep.addOpening("34", 4);
        HashRep.addOpening("344", 4);
        HashRep.addOpening("3444", 4);
        HashRep.addOpening("2", 3);
        HashRep.addOpening("1", 4);
        HashRep.addOpening("44", 4);
        HashRep.addOpening("447", 5);
        HashRep.addOpening("441", 3);
        HashRep.addOpening("443", 2);
        HashRep.addOpening("44323", 3);
        HashRep.addOpening("4432334", 3);
        HashRep.addOpening("4432335", 6);
        HashRep.addOpening("445", 6);
        HashRep.addOpening("44565", 5);
        HashRep.addOpening("4456554", 5);
        HashRep.addOpening("4456553", 2);
        HashRep.addOpening("44325", 6);
        HashRep.addOpening("44523", 2);
        HashRep.addOpening("444", 4);
        HashRep.addOpening("4444", 4);
        HashRep.addOpening("44444", rndMv(new int[]{1, 2, 3, 4, 5, 6, 7}));
        HashRep.addOpening("444447", 3);
        HashRep.addOpening("4444473", 2);
        HashRep.addOpening("44444732", 2);
        HashRep.addOpening("444447326", 5);
        HashRep.addOpening("444447322", 2);
        HashRep.addOpening("4444473222", 2);
        HashRep.addOpening("44444732222", 6);
        HashRep.addOpening("444447322226", 6);
        HashRep.addOpening("4444473222266", 6);
        HashRep.addOpening("44444732222666", 6);
        HashRep.addOpening("444447322226666", 4);
        HashRep.addOpening("4444473222266664", rndMv(new int[]{1, 2, 3, 4, 5, 6, 7}));
        HashRep.addOpening("444441", 5);
        HashRep.addOpening("4444415", 6);
        HashRep.addOpening("44444156", 6);
        HashRep.addOpening("444441566", 6);
        HashRep.addOpening("4444415666", 6);
        HashRep.addOpening("44444156666", 2);
        HashRep.addOpening("444441566662", 2);
        HashRep.addOpening("4444415666622", 2);
        HashRep.addOpening("44444156666222", 2);
        HashRep.addOpening("444441566662222", 4);
        HashRep.addOpening("4444415666622224", rndMv(new int[]{1, 2, 3, 4, 5, 6, 7}));
        HashRep.addOpening("444442", 2);
        HashRep.addOpening("4444422", 2);
        HashRep.addOpening("44444222", 2);
        HashRep.addOpening("444442222", 6);
        HashRep.addOpening("4444422226", 6);
        HashRep.addOpening("44444222266", 6);
        HashRep.addOpening("444442222666", 6);
        HashRep.addOpening("444446", 6);
        HashRep.addOpening("4444466", 6);
        HashRep.addOpening("44444666", 6);
        HashRep.addOpening("444446666", 2);
        HashRep.addOpening("4444466662", 2);
        HashRep.addOpening("44444666622", 2);
        HashRep.addOpening("444446666222", 2);
        HashRep.addOpening("444443", 3);
        HashRep.addOpening("4444433", 3);
        HashRep.addOpening("44444333", 3);
        HashRep.addOpening("444443333", 3);
        HashRep.addOpening("444445", 5);
        HashRep.addOpening("4444455", 5);
        HashRep.addOpening("44444555", 5);
        HashRep.addOpening("444445555", 5);
        HashRep.addOpening("4444455555", 2);
        HashRep.addOpening("44444555552", 1);
        HashRep.addOpening("444445555521", 7);
        HashRep.addOpening("444444", 3);
        HashRep.addOpening("4444443", 2);
        HashRep.addOpening("44444435", 5);
        HashRep.addOpening("44444432", 6);
        HashRep.addOpening("444444326", 5);
        HashRep.addOpening("4444443265", 5);
        HashRep.addOpening("444443", 3);
        HashRep.addOpening("4444433", 3);
        HashRep.addOpening("44444333", 3);
        HashRep.addOpening("444443333", 3);
        HashRep.addOpening("4444433333", 6);
        HashRep.addOpening("44444333336", 7);
        HashRep.addOpening("444443333367", 1);
        HashRep.addOpening("474442", 2);
        HashRep.addOpening("414446", 6);
        HashRep.addOpening("47444227", 1);
        HashRep.addOpening("41444661", 7);
        HashRep.addOpening("743", 4);
        HashRep.addOpening("74343", 4);
        HashRep.addOpening("42", 2);
        HashRep.addOpening("46", 6);
        HashRep.addOpening("4444422226666", rndMv(new int[]{2, 4, 6}));
        HashRep.addOpening("444442222666642", 6);
        HashRep.addOpening("444442222666646", 2);
        HashRep.addOpening("4444466662222", rndMv(new int[]{2, 4, 6}));
        HashRep.addOpening("444446666222242", 6);
        HashRep.addOpening("444446666222246", 2);
        HashRep.addOpening("444441566661", 2);
        HashRep.addOpening("44444156666123", 3);
        HashRep.addOpening("545", 5);
        HashRep.addOpening("343", 3);
        HashRep.addOpening("4444426", 5);
        HashRep.addOpening("444442655", 5);
        HashRep.addOpening("44444265555", 5);
        HashRep.addOpening("4444462", 3);
        HashRep.addOpening("444446233", 3);
        HashRep.addOpening("44444623333", 3);
        HashRep.addOpening("45", 2);
        HashRep.addOpening("4521", 2);
        HashRep.addOpening("4523", 3);
        HashRep.addOpening("43", 6);
        HashRep.addOpening("4367", 6);
        HashRep.addOpening("4365", 5);

        starter();

    }

    public static int rndMv(int[] moves) {
        return moves[ThreadLocalRandom.current().nextInt(0, moves.length)];
    }

    public static void starter(int what) throws ExecutionException, InterruptedException {
        if (what == 0) {
            DataScraper.getData(10, 100, Board.getInstance(), false);
        } else if (what == 1) {
            int[] board = {0, 0, 0, -1, 0, 1, 0, 0, 0, -1, 1, 0, -1, 0, 0, 0, 1, -1, 1, 1, 0, 0, -1, -1, 1, -1, -1, 0, 1, 1, -1, -1, 1, 1, 0, -1, -1, 1, 1, 1, -1, 0};
            Board.getInstance().loadPosition(board);
            Window.startMenu(false);
            while (!gameStart) {
                Thread.onSpinWait();
            }
            Window.gameWindow();
            Board.getInstance().gameLoop(pt1, pt2, Board.getInstance().getPlayingBoard(), true, depth, true);
        } else {
            Window.startMenu(false);
            while (!gameStart) {
                Thread.onSpinWait();
            }
            Window.gameWindow();
            Board.getInstance().gameLoop(pt1, pt2, Board.getInstance().getPlayingBoard(), true, depth, true);
        }
    }

    public static void starter() throws ExecutionException, InterruptedException {
        starter(69420);
    }

}
