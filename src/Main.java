import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Main {

    static volatile boolean gameStart;
    static int pt1 = 0;
    static int pt2 = 0;
    static int depth = 10;

    public static void main (String[] args) throws ExecutionException, InterruptedException {

        int[] arr1 = {10, 20, 30};
        System.out.println("arr1 Before copying: " + Arrays.toString(arr1));

        int[] arr2 = new int[arr1.length];

        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        System.out.println("arr2 After copying: " + Arrays.toString(arr1));

        arr2[0] = 30;
        System.out.println("arr1 after copying and changing an element in arr2: " + Arrays.toString(arr1));

        System.out.println("Running Connect Four by CallMeLee...");
        starter();

    }

    public static void starter(int what) throws ExecutionException, InterruptedException {
        if (what == 0) {
            DataScraper.getData(10, 100, Board.getInstance());
        } else if (what == 1) {
            int[] board = {0, 1, -1, -1, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 1, 1, -1, 0, 0, 0, 1, -1, 1, -1, -1, 0, 0, 1, 1, -1, 1, -1, 1, 1};
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

    public static void starter() throws ExecutionException, InterruptedException {
        Window.startMenu();
        while (!gameStart) {
            Thread.onSpinWait();
        }
        Window.gameWindow();
        Board.getInstance().gameLoop(pt1, pt2, Board.getInstance().getPlayingBoard(), true, depth, true);
    }

}
