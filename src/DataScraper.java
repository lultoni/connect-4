import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutionException;

public class DataScraper {

    // Depth (DataScraper)
    // Time (Timer)
    // Winner (Board (evaluate))
    // Free Fields (DataScraper)
    public static void getData(int depth, int gamesToPlay, Board game, boolean print) throws ExecutionException, InterruptedException {
        System.out.println("Fetching " + gamesToPlay + " games with a depth of " + depth + ".");
        for (int i = 0; i < gamesToPlay; i++) {
            System.out.println("> Currently on game " + (i + 1) + " (" + (gamesToPlay - i) + " to play)");
            game.gameLoop(2, 2, new int[42], print, depth, false);
            String winner;
            if (game.evaluate() == 999999) {
                winner = "White";
            } else if (Board.getInstance().evaluate() == -999999) {
                winner = "Black";
            } else {
                winner = "Draw";
            }
            String dataLine = depth + " " + Timer.averageMs() + " " + winner + " " + countFreeFields(game);
            System.out.println(">res> " + dataLine);
            dataLine += "\n";
            try {
                Files.write(Paths.get("data3.txt"), dataLine.getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                System.out.println("THROW IO BRO!!!");
            }
            Board.getInstance().gameMoves = "";
        }
        System.out.println("Ready to import.");
    }

    private static int countFreeFields(Board game) {
        int counter = 0;
        for (int f: game.getPlayingBoard()) {
            if (f == 0) {
                counter++;
            }
        }
        return counter;
    }

}
