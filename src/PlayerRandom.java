public class PlayerRandom extends Player{

    public PlayerRandom() {
        super(0);
    }

    @Override
    public int fetchMove(boolean print) {
        while (true) {
            int column = java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 7 + 1);
            int[] playingBoard = Board.getInstance().getPlayingBoard();
            for (int i = 5; i >= 0; i--) {
                if (playingBoard[i * 6 + column - 1 + i] == 0) {
                    return column;
                }
            }
        }
    }
}
