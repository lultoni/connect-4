public class Main {

    static volatile boolean gameStart;
    static int pt1 = 0;
    static int pt2 = 0;
    static int depth = 10;

    public static void main (String[] args) {

        System.out.println("Running Connect Four by CallMeLee...");
        starter(0);

    }

    public static void starter(int what) {
        if (what == 0) {
            DataScraper.getData(10, 1000, Board.getInstance());
        } else if (what == 1) {
            int[] board = {0,0,0,0,0,0,0,  0,1,0,1,0,-1,0,  0,-1,0,-1,0,-1,0,  0,-1,0,1,0,-1,0,  0,1,0,-1,1,1,0,  0,-1,1,1,-1,1,0};
            Board.getInstance().loadPosition(board);
            Window.startMenu();
            while (!gameStart) {
                Thread.onSpinWait();
            }
            Window.gameWindow();
            Board.getInstance().gameLoop(pt1, pt2, Board.getInstance().getPlayingBoard(), true, depth, true);
        } else {
            starter();
        }
    }

    public static void starter() {
        Window.startMenu();
        while (!gameStart) {
            Thread.onSpinWait();
        }
        Window.gameWindow();
        Board.getInstance().gameLoop(pt1, pt2, Board.getInstance().getPlayingBoard(), true, depth, true);
    }

}
