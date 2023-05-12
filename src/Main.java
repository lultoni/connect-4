public class Main {

    public static void main (String[] args) {

        System.out.println("Running Connect Four by CallMeLee...");
        int[] board = {
                0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,
                0,0,0,-1,0,0,0,
                0,0,0,-1,0,0,0,
                0,0,1,-1,0,0,0,
                0,-1,1,1,1,-1,1};
        Board.getInstance().loadPosition(board);
        int pt1 = 2;
        int pt2 = 0;
        int depth = 10;
        Board.getInstance().gameLoop(new Player(pt1, depth), new Player(pt2, depth));

    }

}
