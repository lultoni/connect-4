public class Main {

    public static void main (String[] args) {

        System.out.println("Running Connect Four by CallMeLee...");
        Board.getInstance().gameLoop(new Player(0, 5), new Player(2, 5));

    }

}
