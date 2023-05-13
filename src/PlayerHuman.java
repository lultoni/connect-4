import java.util.Scanner;

public class PlayerHuman extends Player {
    public PlayerHuman() {
        super(0);
    }

    static int column;

    @Override
    public int fetchMove(boolean print) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("In which column do you want to place?");
                // Number between 1 and 7
                column = Integer.parseInt(scanner.next());
                if (column < 1 || column > 7) {
                    throw new java.lang.NumberFormatException();
                }
                // Is the column free?
                int[] playingBoard = Board.getInstance().getPlayingBoard();
                for (int i = 5; i >= 0; i--) {
                    if (playingBoard[i * 6 + column - 1 + i] == 0) {
                        return column;
                    } else if (i == 0 && playingBoard[column - 1 + i] != 0) {
                        throw new java.lang.NumberFormatException();
                    }
                }
            } catch (java.lang.NumberFormatException use) {
                System.out.println("Not a correct input.");
            }

        }
    }

}
