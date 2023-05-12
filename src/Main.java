public class Main {

    public static void main (String[] args) {

        System.out.println("Running Connect Four by CallMeLee...");
        //O O X O - - -
        //X X O O - - -
        //O X O O - X O
        //O O O X - O X
        //X X X O X X X
        //O X X O O X X
        int[] board = {
                -1,-1,1,-1,0,0,0,
                1,1,-1,-1,0,0,0,
                -1,1,-1,-1,0,1,-1,
                -1,-1,-1,1,0,-1,1,
                1,1,1,-1,1,1,1,
                -1,1,1,-1,-1,1,1};
//        Board.getInstance().loadPosition(board);
        int pt1 = 2;
        int pt2 = 2;
        int depth = 10;
        Board.getInstance().gameLoop(new Player(pt1, depth - 1), new Player(pt2, depth - 1));

    }

}
